아래 정보를 바탕으로 내일 하루 동안의 일기를 생성해주세요.

1. 기본 정보:
   - 내일 날짜: $tomorrow_day$
   - 날씨: $tomorrow_weather$
   - 이동 수단: $transport_type$

2. 활동 시간 정보:
   - 기상 시간: $wakeup_time$
   - 출근 시간: $start_time$
   - 퇴근 시간: $end_time$
   - 퇴근 후 시간: $enjoy_time$

3. 주요 장소 정보:
   - 출발 위치: 위도 $start_latitude$, 경도 $start_longitude$
   - 목적지 이름: $destination$
   - 들를 장소:
     - 첫 번째 장소: $first_facility$
     - 두 번째 장소: $second_facility$

---

### 요청사항:
1. 내일 하루 동안의 활동을 시간 순서대로 총 4개의 `contents`로 작성해주세요.
2. 각 `content`에는 다음을 포함해주세요:
   - 시간(`time`): 활동이 이루어진 시간.
   - 내용(`content`): 한 일, 방문한 장소, 느낀 감정 등을 서술.
3. 하루를 마무리하며 느낀 감정을 `finalContent`에 간략히 작성해주세요.

---

### 결과 형식(JSON):
```json
<\{
    "contents": [
      <\{
        "time": "$wakeup_time$",
        "content": "아침에 $wakeup_time$에 일어났다. 날씨가 $tomorrow_weather$라서 기분 좋게 하루를 시작했다.",
        "imagePrompt": "아침에 $wakeup_time$에 일어나는 장면. 맑고 쾌적한 $tomorrow_weather$ 날씨가 창밖으로 보이며, 따뜻한 침실에서 사람이 기분 좋게 기상하는 모습."
      \}>,
      <\{
        "time": "$start_time$",
        "content": "출근 시간에 $transport_type$를 이용해 $destination$으로 이동했다.",
        "imagePrompt": "$start_time$에 $transport_type$를 이용해 출근하는 장면. 번화한 도로를 지나며, $destination$ 근처의 현대적인 건물이 보이는 상황. 일상적인 출근길의 바쁜 분위기."
      \}>,
      <\{
        "time": "$end_time$",
        "content": "퇴근 후 $first_facility$에 들러서 친구들과 대화를 나눴다.",
        "imagePrompt": "$end_time$에 퇴근 후 $first_facility$에서 친구들과 대화를 나누는 장면. 따뜻하고 아늑한 카페 내부 분위기."
      \}>,
      <\{
        "time": "$enjoy_time$",
        "content": "$second_facility$에 가서 운동을 했다. 하루를 정리하며 만족감을 느꼈다.",
        "imagePrompt": "저녁 $enjoy_time$에 $second_facility$ 체육관에서 운동하는 장면. 밝게 조명이 켜진 체육관에서 사람이 러닝머신을 뛰거나 웨이트를 드는 모습. 활기차고 에너지 넘치는 분위기."
      \}>
    ],
    "summary": "오늘 하루는 바쁘지만 보람찼다."
\}>

