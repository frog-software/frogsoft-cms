<script setup>
import Carousel from '../components/HomeBody/Carousel.vue';
import HotArticles from '../components/HomeBody/HotArticles.vue';
</script>
<template>
  <div class="body">
    <a-row
        align="middle"
        justify="center"
        style="padding-top:10px;padding-bottom:10px;padding-right:0px;padding-left:0px;"
        type="flex"
    >
      <a-col span="23">
        <carousel/>
      </a-col>
    </a-row>

    <a-row
        align="middle"
        justify="center"
        style="padding-top:10px;padding-bottom:20px;padding-right:0px;padding-left:0px;"
        type="flex"
    >
      <a-col span="24">
        <a-card
            hoverable
            style="height:300px;text-align: center"
        >
          <img
              alt="网站 LOGO"
              src="../assets/logo.png"
              style="width: 40%"
          >
          <a-input-search
              v-model:value="searchContent"
              placeholder="一键检索你想知道的"
              style="width:70%"
              @search="$router.push({ name: 'Search', query: { key: searchContent } })"
          />
          <div style="text-align: left;font-size: 8px;margin:20px 20px;line-height: 8px">
            <p>目前支持搜拼音、搜单字、搜词语、搜文章功能。</p>
            <p>搜拼音：将输入视为拼音，搜索可能对应的汉字</p>
            <p>搜单字：获取输入中每一个汉字的读音</p>
            <p>搜词语：检索与输入相关的词语</p>
            <p>搜文章：检索与输入相关的文章</p>
          </div>
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
      toolList: [
        {
          id: 0,
          img: 'http://dummyimage.com/400x300',
          name: '条件检字',
          description: '声韵调检索汉字',
          routerName: 'Conditions',
          able: true,
        },
        {
          id: 1,
          img: 'http://dummyimage.com/400x300',
          name: '方言词典',
          description: '提供方言词语查询',
          routerName: '/tools/wordDict',
          able: true,
        },
        {
          id: 2,
          img: 'http://dummyimage.com/400x300',
          name: '方言语音识别工具',
          description: '努力研发中',
          routerName: '/tools/Voice',
          able: false,
        },
      ],
    };
  },
};
</script>
