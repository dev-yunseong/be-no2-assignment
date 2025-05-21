# be-no2-assignment

Kakao Tech Campus Assignment

## API 명세서
|기능|Method|URL| Query                                           | Request            |Response|Status Code|
|--|---|---|-------------------------------------------------|--------------------|--------|---|
|일정 추가 |POST|/api/schedules |                                                 | ScheduleRequestDto | ScheduleResponseDto       |201|}
|전체 일정 조회|GET|/api/schedules| writer or modificatedDate or pageNo or pageSize |                    | List<ScheduleResponseDto> |200|
|일정 조회|GET|/api/schedules/{id} |                                                 |                    | ScheduleResponseDto       |200|
|일정 수정|PUT|/api/schdules/{id} |                                                 | ScheduleRequestDto | ScheduleResponseDto       |200|
|일정 삭제|DELETE|/api/schedules/{id} |                                                 |                    | ScheduleResponseDto       |200|
|사용자 추가|POST|/api/users |                                                 | UserRequestDto     | UserResponseDto          |201|
|전체 사용자 조회|GET|/api/users |                                                 |                    | List<UserResponseDto>    |200|

### DTO
|이름| 구조                                                                                                                  |
|--|---------------------------------------------------------------------------------------------------------------------|
|ScheduleRequestDto| {title: String, content: String, userId: Long, password: String}                                                    |
|ScheduleResponseDto| {id: Long, title: String, content: String, writer: String, createdDate: LocalDateTime, modifiedDate: LocalDateTime} |
|UserRequestDto| {name: String, email: String}                                                                                       |
|UserResponseDto| {id: Long, name: String, email: String}                                                                             |