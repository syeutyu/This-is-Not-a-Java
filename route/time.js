exports.getTimeStamp = () => {
    let NT_date = new Date();
    let nt_month = NT_date.getMonth() + 1; //month는 0부터 시작함. 1월 = 0, 10월 = 9
    let nt_day = NT_date.getDate(); //day는 현재 일자의 요일을 나타냄. 0 = 일요일 1 = 월요일
    let nt_hour = NT_date.getHours();
    let nt_min = NT_date.getMinutes();
    let nt_sec = NT_date.getSeconds();
    return nt_month + "월 " + nt_day + "일 " + nt_hour + "시 " + nt_min + "분 " + nt_sec + "초";
};