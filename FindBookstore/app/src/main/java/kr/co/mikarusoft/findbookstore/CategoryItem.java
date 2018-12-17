package kr.co.mikarusoft.findbookstore;

public class CategoryItem {

    //문자열 대신 아이템을 생성한다. int 포함의 경우는

    private String cate;
    private int id;

    public CategoryItem(String cate, int id) {
        this.cate = cate;
        this.id = id;
    }

    public String getCate() {
        return cate;
    }

    public int getId() {
        return id;
    }

    public void setCate(String cate) {
        this.cate = cate;
    }

    public void setId(int id) {
        this.id = id;
    }
}
