# 환경 변수(Environment Variable)

## .env

- 환경 변수를 관리하는 파일
- React는 다음과 같은 파일 이름 규칙에 따라 `.env` 파일을 읽는다.
  - .env : 모든 환경에서 읽는다.
  - .env.development : 개발 환경에서 읽는다.(npm run dev)
  - .env.production : 배포 환경에서 읽는다.(npm run build)
- React는 반드시 `REACT_APP_` 으로 시작하는 변수명만 읽을 수 있다.
  - 예시
    - `REACT_APP_API_KEY`
    - `REACT_APP_API_URL`
- React는 빌드 시점에 환경 변수를 읽는다.
  - 즉, `npm run dev` 또는 `npm run build` 명령어 입력 순간 환경 변수가 고정된다.

## 파일 생성과 환경 변수 사용

- .env 파일 생성
  - React 프로젝트 루트 폴더에서 `.env` 파일을 생성한다.
- .env 예제
  ```
  REACT_APP_API_URL=http://localhost:8080
  REACT_APP_API_KEY=1234
  ```
- `process.env`객체를 사용해서 환경 변수를 사용한다.
