<template>
  <div class="my_carousel">
    <a-card
      title="每日一词"
    >
      <template v-slot:cover>
        <img alt="每日一图" :src="cover" @click="$router.push(link)"/>
      </template>
      <template v-slot:extra>
        <router-link :to="link">More</router-link>
      </template>
      <a-card-meta :title="title" :description="description">
      </a-card-meta>
    </a-card>
  </div>
</template>
<script>
import axios from 'axios'

export default {
  name: 'WordOfTheDay',
  data () {
    return {
      // 词语小卡片所需参数
      title: 'Card title', // 卡片标题
      description: 'This is the description这里需要插入单字/词语的描述等', // 内容描述
      cover: 'https://api.mfstudio.cc/bing/', // 卡片图片地址
      link: '/blog',
      id: 2
    }
  },
  created () {
    axios.get('/website/word_of_the_day').then((res) => {
      this.id = res.data.word_of_the_day.id
      this.link = 'words/' + this.id
      this.title = res.data.word_of_the_day.word
      this.description = res.data.word_of_the_day.definition.slice(0, 50)
    })
  }
}
</script>
