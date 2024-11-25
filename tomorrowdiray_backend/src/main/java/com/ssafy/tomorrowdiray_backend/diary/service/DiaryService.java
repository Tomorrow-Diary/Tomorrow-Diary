package com.ssafy.tomorrowdiray_backend.diary.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

import com.ssafy.tomorrowdiray_backend.diary.dto.request.DiaryRequest;
import com.ssafy.tomorrowdiray_backend.diary.dto.response.CreateContentResponse;
import com.ssafy.tomorrowdiray_backend.diary.dto.response.DiaryResponse;
import com.ssafy.tomorrowdiray_backend.diary.entity.*;
import com.ssafy.tomorrowdiray_backend.diary.repository.DiaryRepository;
import com.ssafy.tomorrowdiray_backend.facilities.entity.Facility;
import com.ssafy.tomorrowdiray_backend.facilities.service.FacilityService;
import com.ssafy.tomorrowdiray_backend.global.util.JsonUtil;
import com.ssafy.tomorrowdiray_backend.house.entity.House;
import com.ssafy.tomorrowdiray_backend.house.service.HouseService;
import com.ssafy.tomorrowdiray_backend.user.entity.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DiaryService {

    private final ChatService chatService;
    private final DalleService dalleService;
    private final FacilityService facilityService;
    private final HouseService houseService;
    private final DiaryRepository diaryRepository;

    @Transactional
    public DiaryResponse createDiary(DiaryRequest request, User user) {
        // 랜덤 시설 정보 가져오기
        Facility firstFacility = facilityService.getRandomFacility(request.getLatitude(), request.getLongitude(),
                request.getFacilities().get(0));
        Facility secondFacility = facilityService.getRandomFacility(request.getLatitude(), request.getLongitude(),
                request.getFacilities().get(1));
        // 날씨 및 날짜 정보 생성
        String weather = Weather.getRandomWeather();
        LocalDate tomorrow = LocalDate.now().plusDays(1);

        // 텍스트 응답 생성
        String createdByChat = chatService.generateDiaryContent(request, user, firstFacility, secondFacility, weather, tomorrow);

        // String 객체 변환
        Map<String, Object> createByChatToMap = JsonUtil.parseJsonToMap(createdByChat);
        List<CreateContentResponse> contents = mapToDto(createByChatToMap);
        String summary = (String) createByChatToMap.get("summary");

        // 이미지 생성
        List<String> imageUrlList = dalleService.generateImage(contents, summary);

        // 일기 저장
        House house = houseService.getHouseByLocation(request.getLatitude(), request.getLongitude());
        Diary diary = Diary.toEntity(tomorrow, weather, user, house);
        diaryRepository.insertDiary(diary);

        // DiaryContent 저장
        insertDiaryContents(contents, diary, summary);

        // DiaryFacility 저장
        insertDiaryFacilities(List.of(firstFacility, secondFacility), diary);

        // DiaryImage 저장
        insertDiaryImages(imageUrlList, diary);

        return DiaryResponse.toDto(tomorrow, weather, contents, summary, imageUrlList);
    }

    private void insertDiaryImages(List<String> imageUrlList, Diary diary) {
        List<DiaryImage> diaryImageList = new ArrayList<>();
        for (String imgUrl : imageUrlList) {
            diaryImageList.add(DiaryImage.toEntity(false, imgUrl, diary));
        }
        diaryRepository.insertDiaryImages(diaryImageList);
    }

    private void insertDiaryFacilities(List<Facility> facilities, Diary diary) {
        List<DiaryFacility> diaryFacilities = new ArrayList<>();
        for (Facility facility : facilities) {
            diaryFacilities.add(DiaryFacility.toEntity(diary, facility));
        }
        diaryRepository.insertDiaryFacilities(diaryFacilities);
    }


    private static ArrayList<CreateContentResponse> mapToDto(Map<String, Object> createByChatToMap) {
        List<Map<String, String>> rawContents = (List<Map<String, String>>) createByChatToMap.get("contents");

        return rawContents.stream()
                .map(rawContent -> CreateContentResponse.builder()
                        .time(LocalTime.parse(rawContent.get("time")))
                        .content(rawContent.get("content"))
                        .build())
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private void insertDiaryContents(List<CreateContentResponse> contents, Diary diary, String summary) {
        List<DiaryContent> diaryContentList = contents.stream()
                .map(content -> DiaryContent.toEntity(content.getContent(), false, diary, content.getTime()))
                .collect(Collectors.toCollection(ArrayList::new));
        diaryContentList.add(DiaryContent.toEntity(summary, true, diary));

        diaryRepository.insertDiaryContents(diaryContentList);
    }
}