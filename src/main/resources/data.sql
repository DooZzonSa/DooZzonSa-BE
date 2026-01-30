-- PolicyIssue 더미 데이터

-- 이번 달 (2026년 1월) - 정보 유출
INSERT INTO policy_issue (title, issue_type, summary, source, url, issue_date)
VALUES ('대형 쇼핑몰 A사, 고객 개인정보 230만건 유출', 'DATA_BREACH', '이름, 연락처, 주소 등 개인정보가 해킹으로 유출. 2차 피해 주의 필요', '디지털 타임즈',
        'https://example.com/news/1', '2026-01-28');

INSERT INTO policy_issue (title, issue_type, summary, source, url, issue_date)
VALUES ('B금융앱, 사용자 결제 정보 18만건 해킹 피해', 'DATA_BREACH', '신용카드 정보를 포함한 결제 데이터가 다크웹에 유출. 카드 재발급 권고', 'IT뉴스',
        'https://example.com/news/2', '2026-01-25');

INSERT INTO policy_issue (title, issue_type, summary, source, url, issue_date)
VALUES ('C배달앱, 주문 내역 및 위치 정보 50만건 노출', 'DATA_BREACH', '주문 기록, 배달 주소, GPS 정보가 보안 취약점으로 인해 노출됨', '테크리포트',
        'https://example.com/news/3', '2026-01-22');

INSERT INTO policy_issue (title, issue_type, summary, source, url, issue_date)
VALUES ('D헬스케어 서비스, 건강 데이터 120만건 유출', 'DATA_BREACH', '건강 검진 결과, 진료 기록 등 민감 정보가 랜섬웨어 공격으로 유출', '메디컬뉴스',
        'https://example.com/news/4', '2026-01-18');

INSERT INTO policy_issue (title, issue_type, summary, source, url, issue_date)
VALUES ('E쇼핑몰, 고객 생년월일 및 성별 정보 80만건 노출', 'DATA_BREACH', '회원가입 시 수집한 개인식별정보가 내부 직원 실수로 외부 유출', '경제일보',
        'https://example.com/news/5', '2026-01-15');

-- 이번 달 (2026년 1월) - 약관 악용
INSERT INTO policy_issue (title, issue_type, summary, source, url, issue_date)
VALUES ('F금융앱, 동의 없이 광고사에 정보 판매 적발', 'ABUSE', '이용자 동의 없이 개인정보를 제3자 광고회사에 판매한 사실이 밝혀짐', '파이낸셜뉴스',
        'https://example.com/news/6', '2026-01-27');

INSERT INTO policy_issue (title, issue_type, summary, source, url, issue_date)
VALUES ('G소셜미디어, 약관 변경으로 위치 정보 무단 수집', 'ABUSE', '앱 업데이트 시 약관을 일방적으로 변경하여 위치 정보 수집 범위 확대', 'SNS워치',
        'https://example.com/news/7', '2026-01-24');

INSERT INTO policy_issue (title, issue_type, summary, source, url, issue_date)
VALUES ('H게임사, 미성년자 개인정보 부모 동의 없이 수집', 'ABUSE', '법정대리인 동의 절차 없이 14세 미만 아동의 정보를 수집하여 과태료 부과', '게임타임즈',
        'https://example.com/news/8', '2026-01-20');

INSERT INTO policy_issue (title, issue_type, summary, source, url, issue_date)
VALUES ('I통신사, 마케팅 수신 거부에도 문자 발송 지속', 'ABUSE', '수신 거부 의사를 밝힌 고객에게도 지속적으로 마케팅 문자를 발송하여 논란', '통신뉴스',
        'https://example.com/news/9', '2026-01-17');

INSERT INTO policy_issue (title, issue_type, summary, source, url, issue_date)
VALUES ('J여행앱, 약관에 명시되지 않은 개인정보 제3자 제공', 'ABUSE', '이용약관에 명시하지 않은 제휴사에 고객 정보를 제공하여 개인정보보호법 위반', '트래블데일리',
        'https://example.com/news/10', '2026-01-13');

INSERT INTO policy_issue (title, issue_type, summary, source, url, issue_date)
VALUES ('K음악 스트리밍, 청취 기록으로 성향 분석 후 프로파일링', 'ABUSE', '사용자의 음악 취향 데이터를 분석하여 정치 성향 등을 프로파일링한 사실 발각', '뮤직뉴스',
        'https://example.com/news/11', '2026-01-10');

INSERT INTO policy_issue (title, issue_type, summary, source, url, issue_date)
VALUES ('L쇼핑 플랫폼, 구매 내역 기반 타겟 광고 무단 실시', 'ABUSE', '구매 이력을 분석하여 고객 동의 없이 맞춤형 광고에 활용', '커머스투데이',
        'https://example.com/news/12', '2026-01-05');

-- 이전 달 (2025년 12월) - 정보 유출
INSERT INTO policy_issue (title, issue_type, summary, source, url, issue_date)
VALUES ('M은행, 계좌 정보 340만건 해킹으로 유출', 'DATA_BREACH', '고객 계좌번호 및 잔액 정보가 APT 공격으로 유출됨', '금융타임즈',
        'https://example.com/news/13', '2025-12-28');

INSERT INTO policy_issue (title, issue_type, summary, source, url, issue_date)
VALUES ('N병원, 환자 진료 기록 95만건 랜섬웨어 피해', 'DATA_BREACH', '병력, 진단명, 처방 내역 등이 암호화되어 복구 불가능 상태', '헬스케어뉴스',
        'https://example.com/news/14', '2025-12-20');

INSERT INTO policy_issue (title, issue_type, summary, source, url, issue_date)
VALUES ('O항공사, 마일리지 회원 정보 180만건 유출', 'DATA_BREACH', '여권번호, 탑승 이력 등 민감정보가 외부 유출', '항공신문', 'https://example.com/news/15',
        '2025-12-15');
