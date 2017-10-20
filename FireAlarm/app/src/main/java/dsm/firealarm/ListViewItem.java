package dsm.firealarm;

import android.graphics.drawable.Drawable;

import java.text.Collator;
import java.util.Comparator;

/**
 * Created by 10107PSH on 2017-08-28.
 */

public class ListViewItem {
    /**
     * 리스트 정보를 담고 있을 객체 생성
     */
    public String nameStr; // 유저 네임
    public String spotStr; // 화재 발생 장소
    public String timeStr; // 화재 발생 시각
    public String codeStr;

    public ListViewItem(String nameStr, String spotStr, String timeStr, String codeStr) {
        this.nameStr = nameStr;
        this.spotStr = spotStr;
        this.timeStr = timeStr;
        this.codeStr = codeStr;
    }

    public String getNameStr() {
        return nameStr;
    }

    public void setNameStr(String nameStr) {
        this.nameStr = nameStr;
    }

    public String getSpotStr() {
        return spotStr;
    }

    public void setSpotStr(String spotStr) {
        this.spotStr = spotStr;
    }

    public String getTimeStr() {
        return timeStr;
    }

    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }

    public String getCodeStr() {return codeStr;}

    public void setCodeStr(String codeStr) {this.codeStr = codeStr;}
}
