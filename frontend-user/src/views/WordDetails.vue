<template>
  <a-spin :spinning="spinning" :delay="500">
    <a-card>

      <template v-slot:title>
        <h1 style="padding-left:30px; color: rgb(26,26,73); font-size:250%"><strong> {{ word.word }} </strong></h1>
        <!--   TODO:增加语音内容     -->
        <!--      <span style="font-size: 100%"> {{ pinyin }} </span>-->
        <!--      <span style="font-size: 100%;color: rgb(155,155,155) "> / {{ IPA }} / </span>-->
      </template>

      <template v-slot:extra>
        来源:
        <router-link :to="{name:'UserDetails',params:{id:word.contributor.id.toString()}}">
          {{ word.contributor.nickname }}
        </router-link>
      </template>

      <!--释义-->
      <div>
        <div style="padding:15px">
          <a-tag color="rgb(179, 7, 30,0.7)"> 释义</a-tag>
        </div>
        <div
          style="width: 100%;padding: 20px"
          v-for="(item,index) in analysedDefinition"
          :key="index+1"
        >
          <a-card
            v-if="item.example.length"
          >
            <template v-slot:title>
              {{ analysedDefinition.length > 1 ? (index + 1) + ':' : '' }}{{ item.content }}
            </template>
            <div v-for="exp in item.example" :key="exp.content" style="padding: 5px">
              <a-tag color="rgb(64, 49, 131)"> {{ exp.type }}</a-tag>
              <span style="padding:5px">
            <strong> {{ exp.content }} </strong>
            <span style="font-size:x-small"> {{ exp.explain }} </span>
          </span>
            </div>
          </a-card>
          <a-card v-else>
            {{ analysedDefinition.length > 1 ? (index + 1) + ':' : '' }}
            {{ analysedDefinition[0].content }}
          </a-card>
        </div>
      </div>

      <!--相关文章-->
      <ArticleList
        v-if="word.related_articles.length"
        :list-data="word.related_articles"
        :page-size="3"
      />
    </a-card>
  </a-spin>
</template>

<script>

import axios from 'axios'
import ArticleList from '@/components/Articles/ArticleList'

export default {
  name: 'WordDetails',
  props: ['id'],
  components: { ArticleList },
  data () {
    return {
      spinning: false,
      analysedDefinition: [{
        content: 'content',
        example: [
          {
            type: '例',
            content: '',
            explain: ''
          }
        ]
      }
      ],
      word: {
        id: 0,
        word: '例子',
        definition: '这仅仅是一个样例',
        contributor: {
          nickname: '用户昵称',
          avatar: 'http://dummyimage.com/100x100',
          id: 0
        },
        annotation: '',
        mandarin: [],
        related_words: [],
        related_articles: [],
        views: 100
      }
    }
  },
  created () {
    this.getWordDetails()
  },
  watch: {
    $route () {
      this.getWordDetails()
    }
  },
  methods: {
    /**
     * 获取当前这个词条的具体信息
     */
    getWordDetails () {
      this.spinning = true
      axios.get('words/' + this.id).then(res => {
        this.word = res.data.word
        this.analysedDefinition = this.splitDefinition(this.word.definition)
      }).catch(() => {
        this.$message.destroy()
        this.$router.replace({ name: 'NotFound' })
      }).finally(() => {
        this.spinning = false
      })
    },
    /**
     * 具体解析一个待解析的字符串
     * @param definition 具体待解析的字符串
     */
    analyseDefinition (definition) {
      let index = definition.indexOf('：')
      if (index === -1) index = definition.length
      const result = {
        content: definition.substring(0, index),
        example: []
      }
      let status = 1 // 1：现在是例子；0：现在是例子的解释
      let example = {
        type: '例',
        content: '',
        explain: ''
      }

      for (const char of definition.substring(index + 1)) {
        if (char === '（') {
          // 例子解释开始
          status = 0
        } else if (char === '）') {
          // 例子解释结束
          status = 1
        } else if (status === 0) {
          // 例子解释内容
          example.explain = example.explain + char
        } else if (char === '△') {
          // 例子类型
          example.type = '俗'
        } else if (char === '|' || char === '。') {
          // 一个例子结束
          result.example.push(example)
          example = {
            type: '例',
            content: '',
            explain: ''
          }
        } else {
          // 例子的内容
          example.content = example.content + char
        }
      }
      return result
    },
    /**
     * 将数据库中的释义字符串进行拆分
     * @param definition 释义字符串
     */
    splitDefinition (definition) {
      const order = ['①', '②', '③', '④', '⑤', '⑥', '⑦', '⑧', '⑨', '⑩']
      let lastIndex = -1
      const result = []
      for (let i = 0; i < order.length; i++) {
        const index = definition.indexOf(order[i])
        if (index < 0) break
        if (lastIndex !== -1) {
          result.push(this.analyseDefinition(definition.substring(lastIndex, index)))
        }
        lastIndex = index + 1
      }
      result.push(this.analyseDefinition(definition.substring(lastIndex)))
      return result
    }
  }
}
</script>

<style scoped>

</style>
