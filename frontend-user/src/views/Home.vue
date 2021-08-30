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
      <a-col span="15">
        <a-card hoverable style="height:300px;text-align: center">
          <img src="../assets/logo.png" width="40%">
          <a-input-search
            placeholder="一键检索你想知道的"
            style="width:70%"
            v-model="searchContent"
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
      <a-col span="8">
        <WordOfTheDay/>
      </a-col>
    </a-row>

    <a-row>
      <div v-for="item in toolList" :key="item.id">
        <a-card-grid style="width:33%;text-align:center">
          <a-card @click="$router.push({name:item.routerName})">
            <template v-slot:cover>
              <img :src="item.img" :alt="item.name"/>
            </template>
            <a-card-meta :description="item.description">
            </a-card-meta>
          </a-card>
        </a-card-grid>
      </div>
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
import Carousel from '@/components/HomeBody/Carousel'
import WordOfTheDay from '@/components/HomeBody/WordOfTheDay'
import HotArticles from '@/components/HomeBody/HotArticles'

export default {
  name: 'Home',
  data () {
    return {
      searchContent: '',
      toolList: [
        {
          id: 0,
          img: 'http://dummyimage.com/400x300',
          name: '条件检字',
          description: '声韵调检索汉字',
          routerName: 'Conditions',
          able: true
        },
        {
          id: 1,
          img: 'http://dummyimage.com/400x300',
          name: '方言词典',
          description: '提供方言词语查询',
          routerName: '/tools/wordDict',
          able: true
        },
        {
          id: 2,
          img: 'http://dummyimage.com/400x300',
          name: '方言语音识别工具',
          description: '努力研发中',
          routerName: '/tools/Voice',
          able: false
        }
      ]
    }
  },
  components: {
    Carousel,
    WordOfTheDay,
    HotArticles
  }
}
</script>
