package xyz.ibudai.translation.web.entity;

import lombok.Data;
import xyz.ibudai.translation.core.annotation.Translation;

@Data
public class ProductDetail {

    @Translation
    private String title;

    @Translation
    private String promotion;

    @Translation
    private String visitStore;

    @Translation
    private String reviews;

    @Translation
    private String purchasedLastMonth;

    @Translation
    private String blackFridayOffer;

    @Translation
    private String recommendedPrice;

    @Translation
    private String color;

    @Translation
    private String selectedColor;

    @Translation
    private String withPrime;

    @Translation
    private String viewMoreOptions;

    @Translation
    private String addToCart;

    @Translation
    private String buyNow;


    public static ProductDetail create() {
        ProductDetail productDetail = new ProductDetail();
        productDetail.setTitle("Furologee 带充电站的床头柜,带织物抽屉的床头柜,带 USB 端口和插座的边桌,带储物架和挂钩的床头柜,适用于客厅/卧室,白色");
        productDetail.setPromotion("使用亚马逊 Visa 卡解锁价值 60 美元的礼品卡");
        productDetail.setVisitStore("访问品牌旗舰店");
        productDetail.setReviews("评论");
        productDetail.setPurchasedLastMonth("顾客购买");
        productDetail.setBlackFridayOffer("黑色星期五特价");
        productDetail.setRecommendedPrice("市场价");
        productDetail.setColor("颜色");
        productDetail.setSelectedColor("白色");
        productDetail.setWithPrime("白色");
        productDetail.setViewMoreOptions("查看其它 3 个选项（不含优惠）");
        productDetail.setAddToCart("加入购物车");
        productDetail.setBuyNow("立即购买");
        return productDetail;
    }
}
