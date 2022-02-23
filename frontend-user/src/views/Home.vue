<script setup>
import Carousel    from '../components/HomeBody/Carousel.vue';
import HotArticles from '../components/HomeBody/Announcements.vue';
import store       from '../store';
</script>
<template>
  <div class="body">
    <a-row
        align="middle"
        justify="center"
        style="padding: 10px 0px;"
        type="flex"
    >
      <a-col span="23">
        <carousel/>
      </a-col>
    </a-row>

    <a-row
        align="middle"
        justify="center"
        style="padding: 10px 0px 20px;"
        type="flex"
    >
      <a-col span="24">
        <a-card
            hoverable
            style="text-align: center"
        >
          <img
              :src="store.getters.config.logo"
              alt="网站 LOGO"
              style="max-width: 300px;margin: 30px"
          >
          <a-input-search
              v-model:value="searchContent"
              placeholder="一键检索你想知道的"
              style="width:70%"
              @search="$router.push({ name: 'Search', query: { key: searchContent } })"
          />
          <div style="text-align: left;font-size: 8px;margin:20px 20px;line-height: 8px"/>
        </a-card>
      </a-col>
    </a-row>

    <a-row
        align="top"
        justify="center"
        style="padding-top:0px;padding-bottom:60px;padding-right:0px;padding-left:0px;"
        type="flex"
    >
      <a-col span="24">
        <HotArticles/>
      </a-col>
    </a-row>
  </div>
</template>

<script>

export default {
  name: 'Home',
  data() {
    return {
      searchContent: '',
    };
  },
};
</script>
