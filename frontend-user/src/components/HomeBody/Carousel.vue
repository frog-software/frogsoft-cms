<template>

  <div class="my_carousel">

    <a-carousel
      ref="zmd"
      arrows
      :slideToScroll="current"
      :autoplay="true"
      :autoplaySpeed="6000"
      @click="console.log('carousel')"
    >
      <div
        slot="prevArrow"
        class="custom-slick-arrow"
        style="left: 10px;"
      >
        <a-icon type="left-circle"/>
      </div>

      <div
        slot="nextArrow"
        class="custom-slick-arrow"
        style="right: 10px">
        <a-icon type="right-circle"/>
      </div>
      <!--BUG:触屏下click无反应-->
      <router-link
        style="touch-action: none"
        v-for="item in carousel"
        :key="item.id"
        :to="{name:'ArticleDetails', params:{id:item.id.toString()}}"
      >
        <img :alt="item.id" :src="item.url" style="height: 380px"/>
      </router-link>

    </a-carousel>
  </div>

</template>

<script>
import axios from 'axios'

export default {
  name: 'Carousel',
  data () {
    return {
      carousel: [], // 轮播图上的图片以及对应的链接:[{id,url}]
      current: 0
    }
  },
  created () {
    axios.get('/website/carousel', {}).then((res) => {
      this.carousel = res.data.carousel
    }).catch(err => {
      this.$message.error(err.toString())
    })
  }
}
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
