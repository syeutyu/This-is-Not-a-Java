package dsm.firealarm;

/**
 * Created by 10107박소현 on 2017-08-23.
 */

public class DataModel {

/*
    // 싱글톤으로 파싱할 데이터 만들기
    private DataModel(){}
    private static DataModel dataModel;

    public static DataModel getInstance() {
        if(dataModel==null) {
            dataModel = new DataModel();
        }
        return dataModel;
    }
*/

    /** 테스트 유무 private 선언 */

    /** 화재 발생 시각 private 선언 */

    private String userName, spot;
    // spot = 화재 발생 위치

    public DataModel() {
    }

    public DataModel(String userName, String spot) {
        this.userName = userName;
        this.spot = spot;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSpot() {
        return spot;
    }

    public void setSpot(String spot) {
        this.spot = spot;
    }

    //ArrayList<DataModel> list = new ArrayList<>();
}