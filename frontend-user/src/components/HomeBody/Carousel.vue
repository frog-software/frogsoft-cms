<script setup>
import {
  LeftCircleOutlined,
  RightCircleOutlined,
} from '@ant-design/icons-vue';
</script>
<template>
  <div class="my_carousel">
    <a-carousel
        ref="zmd"
        arrows
        :autoplay-speed="6000"
        :autoplay="true"
        :slide-to-scroll="current"
        @click="console.log('carousel')"
    >
      <template
          #prevArrow
          class="custom-slick-arrow"
          style="left: 10px;"
      >
        <LeftCircleOutlined/>
      </template>

      <template
          #nextArrow
          class="custom-slick-arrow"
          style="right: 10px"
      >
        <RightCircleOutlined/>
      </template>
      <!--BUG:触屏下click无反应-->
      <router-link
          v-for="item in carousel"
          :key="item.id"
          style="touch-action: none"
          :to="{name:'ArticleDetails', params:{id:item.id.toString()}}"
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
import {message} from 'ant-design-vue';

export default {
  name: 'Carousel',
  data() {
    return {
      carousel: [], // 轮播图上的图片以及对应的链接:[{id,url}]
      current: 0,
    };
  },
  created() {
    axios.get('/v1/website/carousel', {}).then((res) => {
      this.carousel = res.data.carousel;
    }).catch((err) => {
      message.error(err.toString());
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
