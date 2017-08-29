package dsm.firealarm;

import android.graphics.drawable.Drawable;

import java.text.Collator;
import java.util.Comparator;

/**
 * Created by 10107PSH on 2017-08-28.
 */

public class ListData {
    /**
     * 리스트 정보를 담고 있을 객체 생성
     */
    // 아이콘. 테스트 아이콘
    public Drawable mTestIcon;

    // 제목. 유저 네임
    public String mName;

    // 날짜. 화재 발생 장소
    public String mSpot;

    // 추가 : 화재 발생 시각
    public String mTime;

    /**
     * 알파벳 이름으로 정렬
     */
    public static final Comparator<ListData> ALPHA_COMPARATOR = new Comparator<ListData>() {
        private final Collator sCollator = Collator.getInstance();

        @Override
        public int compare(ListData mListDate_1, ListData mListDate_2) {
            return sCollator.compare(mListDate_1.mName, mListDate_2.mName);
        }
    };
}
