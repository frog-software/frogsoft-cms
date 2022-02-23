<script setup>
import {LeftCircleOutlined, RightCircleOutlined} from '@ant-design/icons-vue';
</script>
<template>
  <div class="my_carousel">
    <a-carousel
        ref="zmd"
        :autoplay="true"
        :autoplay-speed="6000"
        :slide-to-scroll="current"
        arrows
    >
      <template #prevArrow>
        <LeftCircleOutlined
            class="custom-slick-arrow"
            style="left: 10px;"
        />
      </template>

      <template #nextArrow>
        <RightCircleOutlined
            class="custom-slick-arrow"
            style="right: 10px"
        />
      </template>
      <!--BUG:触屏下click无反应-->
      <router-link
          v-for="item in carousel"
          :key="item.id"
          :to="{name:'ArticleDetails', params:{id:item.id.toString()}}"
          style="touch-action: none"
      >
        <img
            :alt="item.id"
            :src="item.url"
            style="height: 380px"
        >
      </router-link>
    </a-carousel>
  </div>
</template>

<script>
import axios from 'axios';
import { message } from 'ant-design-vue';

export default {
  name: 'Carousel',
  components: {
    LeftCircleOutlined,
    RightCircleOutlined
  },
  data() {
    return {
      carousel: [], // 轮播图上的图片以及对应的链接:[{id,url}]
      current: 0,
    };
  },
  created() {
    this.carousel = [];
    axios.get('/v1/home/daily')
        .then((res) => {
          this.carousel.push({
            id: res.data.id,
            url: res.data.cover
          });
        })
        .catch((err) => {
          if (err.response.status === 404) {
            message.destroy();
          }
        });
    axios.get('/v1/home/recommendations')
        .then((res) => {
          res.data?._embedded?.articleDtoList?.forEach((item) => {
            this.carousel.push({
              id: item.id,
              url: item.cover,
            });
          });
        })
        .catch((err) => {
          if (err.response.status === 404) {
            message.destroy();
          }
        });
  },
};
</script>

<style scoped>
.my_carousel {
  margin: 0 auto;
}

.ant-carousel >>> .slick-slide img {
  display: block;
  margin: auto;
  max-width: 100%;
}

.ant-carousel >>> .custom-slick-arrow {
  width: 50px;
  height: 50px;
  font-size: 50px;
  color: #fff;
  opacity: 0.3;
  z-index: 1000;
}

.ant-carousel >>> .custom-slick-arrow:before {
  display: none;
}

.ant-carousel >>> .custom-slick-arrow:hover {
  opacity: 0.5;
}

.ant-carousel >>> .slick-dots {
  height: auto;
}
</style>
