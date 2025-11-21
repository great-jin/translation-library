<template>
  <div class="amazon-product-page">
    <el-row
        type="flex"
        justify="end"
        align="middle"
        style="padding: 20px 10px;"
    >
      <el-select v-model="language"
                 placeholder="Select"
                 style="width: 120px;"
                 size="small"
                 @change="changeLanguage(language)"
      >
        <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value"
        />
      </el-select>
    </el-row>

    <!-- 产品主要内容区域 -->
    <el-row :gutter="20"
            v-loading="loading"
            class="product-main"
    >
      <!-- 左侧产品图片 -->
      <el-col :span="12">
        <div class="product-image">
          <img
              :src="detail.imageUrl"
              style="width: 400px; height: 500px"
          />
          <div class="image-thumbnails">
            <div
                v-for="(thumb, index) in detail.thumbnails"
                :key="index"
                class="thumbnail"
                :class="{ active: thumb.active }"
            ></div>
          </div>
        </div>
      </el-col>

      <!-- 右侧产品信息 -->
      <el-col :span="12">
        <div class="product-info">
          <!-- 产品标题 -->
          <h1 class="product-title">{{ productData.title }}</h1>

          <!-- 品牌链接 -->
          <div class="brand-link">
            <span>{{ productData.visitStore }}</span>
          </div>

          <!-- 评分 -->
          <div class="rating-section">
            <div class="rating-stars">
              <span
                  v-for="n in 5"
                  :key="n"
                  class="star"
                  :class="{ filled: n <= 4 }"
              >★</span>
              <span class="rating-value">4.2</span>
            </div>
            <div class="rating-count">
              <a>2,936 {{ productData.reviews }}</a>
            </div>
          </div>

          <!-- 购买统计 -->
          <div class="purchase-stats">
            <span class="stats">5 K+ {{ productData.purchasedLastMonth }}</span>
          </div>

          <!-- 黑五促销 -->
          <div class="black-friday">
            <span class="badge">{{ productData.blackFridayOffer }}</span>
            <div class="discount">-41%</div>
          </div>

          <!-- 价格区域 -->
          <div class="pricing-section">
            <div class="current-price">35<sup>99</sup></div>
            <div class="original-price">{{ productData.recommendedPrice }}: 59.99</div>
          </div>

          <!-- 促销信息 -->
          <div class="promotion-card" v-if="productData.promotion">
            <div class="promotion-title">{{ productData.promotion }}</div>
          </div>

          <!-- 颜色选择 -->
          <div class="color-selection">
            <div class="section-title">{{ productData.color }}: <span
                class="selected-color">{{ productData.selectedColor }}</span>
            </div>
          </div>

          <!-- 其他选项 -->
          <div class="other-options">
            <div
                v-for="(option, index) in detail.otherOptions"
                :key="index"
                class="option"
                :class="{ highlighted: option.highlighted }"
            >
              <span class="price">US$ {{ option.price }}</span>
            </div>
          </div>

          <!-- 查看其他选项 -->
          <div class="more-options">
            <a>{{ productData.viewMoreOptions }}</a>
          </div>

          <!-- 操作按钮 -->
          <div class="action-buttons">
            <el-button type="primary" class="add-to-cart-btn">{{ productData.addToCart }}</el-button>
            <el-button class="buy-now-btn">{{ productData.buyNow }}</el-button>
          </div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import logo from '@/assets/1.jpg'
import {getDetail, listProduct} from "@/api/productApi";
import {LANGUAGE_OPTIONS} from "@/data/language";

export default {
  name: 'AmazonProductPage',
  data() {
    return {
      loading: true,
      language: 'zh-CN',
      options: LANGUAGE_OPTIONS,
      detail: {
        imageUrl: logo,
        thumbnails: [
          {active: true},
          {active: false},
          {active: false},
          {active: false}
        ],
        otherOptions: [
          {price: 55.99, highlighted: false, prime: false},
          {price: 37.99, highlighted: true, prime: false},
          {price: 40.00, highlighted: false, prime: true}
        ]
      },
      // 产品主要数据
      productData: {}
    }
  },
  mounted() {
    this.fetchData()
  },
  methods: {
    changeLanguage() {
      this.fetchData()
    },
    fetchData() {
      this.loading = true
      getDetail(this.language).then(res => {
        this.productData = res.data
      }).finally(() => {
        this.loading = false
      })
    },
    addToCart() {
    },
    buyNow() {
    }
  }
}
</script>

<style scoped>
.amazon-product-page {
  width: 100%;
  height: 100%;
  margin: 0 auto;
  font-family: "Amazon Ember", Arial, sans-serif;
  color: #0F1111;
  background-color: #fff;
}

.product-main {
  padding: 20px 40px 0 40px;
}

.product-image {
  text-align: center;
}

.product-image img {
  max-width: 100%;
  height: auto;
  border: 1px solid #ddd;
}

.image-thumbnails {
  display: flex;
  justify-content: center;
  margin-top: 15px;
}

.thumbnail {
  width: 50px;
  height: 50px;
  border: 1px solid #ddd;
  margin: 0 5px;
  cursor: pointer;
}

.thumbnail.active {
  border-color: #e77600;
}

.product-title {
  font-size: 18px;
  font-weight: 400;
  line-height: 1.4;
  margin-bottom: 5px;
}

.brand-link {
  font-size: 14px;
  color: #007185;
  margin-bottom: 10px;
}

.brand-link a {
  color: #007185;
  text-decoration: none;
}

.brand-link a:hover {
  text-decoration: underline;
  color: #c7511f;
}

.rating-section {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
}

.rating-stars {
  display: flex;
  align-items: center;
  margin-right: 10px;
}

.star {
  color: #ddd;
  font-size: 18px;
}

.star.filled {
  color: #FFA41C;
}

.rating-value {
  color: #007185;
  margin-left: 5px;
  font-size: 14px;
}

.rating-count a {
  color: #007185;
  text-decoration: none;
  font-size: 14px;
}

.rating-count a:hover {
  text-decoration: underline;
  color: #c7511f;
}

.amazon-option {
  margin-bottom: 10px;
}

.option-label {
  font-size: 14px;
  color: #565959;
}

.sustainability {
  font-size: 14px;
  color: #007185;
  margin-left: 5px;
}

.purchase-stats {
  margin-bottom: 15px;
}

.stats {
  font-size: 14px;
  color: #565959;
}

.black-friday {
  display: flex;
  align-items: center;
  margin-bottom: 15px;
}

.badge {
  background-color: #CC0C39;
  color: white;
  padding: 3px 8px;
  border-radius: 3px;
  font-size: 12px;
  font-weight: bold;
  margin-right: 10px;
}

.discount {
  color: #CC0C39;
  font-weight: bold;
  font-size: 18px;
}

.pricing-section {
  margin-bottom: 15px;
}

.current-price {
  font-size: 21px;
  color: #B12704;
  font-weight: 400;
}

.current-price sup {
  font-size: 14px;
}

.original-price {
  font-size: 14px;
  color: #565959;
}

.shipping-info {
  font-size: 14px;
  color: #565959;
}

.promotion-card {
  background-color: #F5F5F5;
  border: 1px solid #DDD;
  border-radius: 4px;
  padding: 10px;
  margin-bottom: 15px;
}

.promotion-title {
  font-size: 14px;
  color: #0F1111;
}

.color-selection {
  margin-bottom: 15px;
}

.section-title {
  font-size: 16px;
  margin-bottom: 8px;
}

.selected-color {
  font-weight: bold;
}

.color-options {
  display: flex;
}

.color-option {
  width: 30px;
  height: 30px;
  border-radius: 50%;
  margin-right: 10px;
  cursor: pointer;
  border: 2px solid transparent;
}

.color-option.active {
  border-color: #e77600;
}

.other-options {
  margin-bottom: 15px;
}

.option {
  display: flex;
  align-items: center;
  padding: 8px;
  border: 1px solid #DDD;
  margin-bottom: 5px;
  border-radius: 4px;
}

.option.highlighted {
  background-color: #FEF6E7;
  border-color: #e77600;
}

.option .price {
  font-size: 14px;
  font-weight: bold;
}

.prime-badge {
  background-color: #232F3E;
  color: white;
  font-size: 12px;
  padding: 2px 6px;
  border-radius: 3px;
  margin-left: 10px;
}

.more-options {
  margin-bottom: 20px;
}

.more-options a {
  color: #007185;
  text-decoration: none;
  font-size: 14px;
}

.more-options a:hover {
  text-decoration: underline;
  color: #c7511f;
}

.action-buttons {
  margin-bottom: 15px;
}

.add-to-cart-btn {
  background-color: #FFD814;
  border-color: #FCD200;
  color: #0F1111;
  width: 180px;
  height: 40px;
  margin-right: 10px;
  border-radius: 20px;
  font-weight: bold;
}

.add-to-cart-btn:hover {
  background-color: #F7CA00;
  border-color: #F2C200;
}

.buy-now-btn {
  background-color: #FFA41C;
  border-color: #FF8F00;
  color: #0F1111;
  width: 180px;
  height: 40px;
  border-radius: 20px;
  font-weight: bold;
}

.buy-now-btn:hover {
  background-color: #FA8900;
  border-color: #E37C00;
}

.delivery-info {
  margin-top: 20px;
}

.delivery-option {
  font-size: 14px;
}

.location {
  color: #007185;
  cursor: pointer;
}

.location:hover {
  color: #c7511f;
  text-decoration: underline;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .product-main {
    flex-direction: column;
  }

  .action-buttons {
    display: flex;
    flex-direction: column;
  }

  .add-to-cart-btn, .buy-now-btn {
    width: 100%;
    margin-bottom: 10px;
  }
}
</style>