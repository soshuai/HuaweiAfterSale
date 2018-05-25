package cherish.cn.huaweiaftersale.event;

public class CuntEvent {

   public int count;
   public boolean New;

    public CuntEvent(int count, boolean aNew) {
        this.count = count;
        New = aNew;
    }

    public int getCount() {
        return count;
    }

    public boolean isNew() {
        return New;
    }
}