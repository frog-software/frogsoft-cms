<template>
  <a-spin v-if="hasDeleted===false" :delay="500" :spinning="spinning">
    <a-row :gutter="20" justify="center" type="flex">
      <!--文章主体部分-->
      <a-col span="17">

        <!--标题、封面、简介-->
        <a-card>
          <template #title>
            <h1> {{ article.title }} </h1>
          </template>

          <template #extra>
            <a-button
              v-if="me.is_author"
              :loading="btnDeleteLoading"
              type="primary"
              @click="deleteArticle"
            >
              删除
            </a-button>
            <router-link :to="{name:'ArticleEdit',params:{id: id}}">
              <a-button v-if="me.is_author" type="primary"> 编辑</a-button>
            </router-link>
            <a-button :loading="btnLikeLoading" type="primary" @click="btnLikeClick">
              {{ me.liked ? "取消" : "" }}收藏
            </a-button>
          </template>

          <template #cover>
            <img :alt="'这里本是文章的封面，其地址为'+article.cover+',但是显示不出来了'" :src="article.cover">
          </template>

          <p> 文章简介： {{ article.description }} </p>
        </a-card>

        <!---文章主体部分--->
        <a-card>
          <mavon-editor
            :editable="false"
            :subfield="false"
            :toolbarsFlag="false"
            :value="article.content"
            class="md"
            defaultOpen="preview"
            style="z-index: auto"
          >
          </mavon-editor>
        </a-card>
        <a-card>
          <h3> 发布时间:&nbsp;&nbsp;{{ article.publish_time }} </h3>
          <h3> 最近更新:&nbsp;&nbsp;{{ article.update_time }} </h3>
          <h3>
            <a-icon type="eye"/>
            阅读量：{{ article.views }}
          </h3>
        </a-card>
        <!--评论区-->
        <a-card>
          <template #title>
            <h3> 评论区 </h3>
          </template>
          <a-spin :spinning="commentsLoading" :delay="500">
            <comment-list :parent="0" :pageSize="8" :id="id"/>
          </a-spin>
        </a-card>
      </a-col>

      <!--文章的附加信息-->
      <a-col span="7">
        <!-- 文章的附加信息-->
        <a-card>
          <a-card-meta :title="article.author.nickname">
            <router-link
              slot="avatar"
              :to="{name:'UserDetails',params:{id:article.author.id.toString()}}"
            >
              <a-avatar :src="article.author.avatar"/>
            </router-link>
          </a-card-meta>
        </a-card>

      </a-col>
    </a-row>
  </a-spin>
  <a-result
    v-else
    class="body"
    status="success"
    title="成功删除文章！"
  >
    <template #extra>
      <router-link :to="{name:'Home'}">
        <a-button>返回首页</a-button>
      </router-link>
    </template>
  </a-result>
</template>

<script>
import axios from 'axios'
import { mavonEditor } from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'
import CommentList from '@/components/Articles/CommentList'

export default {
  name: 'ArticleDetails',
  components: { mavonEditor, CommentList },
  props: { id: String },
  data () {
    return {
      spinning: true,
      btnLikeLoading: false,
      btnDeleteLoading: false,
      hasDeleted: false, // 文章是否被删除
      article: {
        id: 0,
        author: {
          id: 0,
          username: 'username',
          nickname: 'nickname',
          email: 'edialect@edialect.top',
          telephone: '',
          registration_time: '2000-01-01 00:00:00',
          login_time: '2000-01-01 00:00:00',
          birthday: '2000-01-01 00:00:00',
          avatar: 'http://dummyimage.com/100x100',
          county: '',
          town: '',
          is_admin: false
        },
        likes: 0,
        views: 0,
        like_users: [],
        publish_time: '2000-01-01 00:00:00',
        update_time: '2000-01-01 00:00:00',
        title: 'title',
        description: 'description',
        content: 'content',
        cover: 'http://dummyimage.com/160x90'
      },
      me: {
        liked: false,
        is_author: false
      }
    }
  },
  computed: {
    commentsLoading () {
      return this.$store.getters.commentsLoading
    }
  },
  created () {
    axios.get('/articles/' + this.id).then(async (res) => {
      this.article = res.data.article
      this.me = res.data.me
      this.$store.commit('updateComments', this.id)
    }).catch(() => {
      this.$message.destroy()
      this.$router.replace({ name: 'NotFound' })
    }).finally(() => {
      this.spinning = false
    })
  },
  beforeRouteEnter (to, from, next) {
    if (to.params.id % 1 === 0) next()
    else next({ name: 'NotFound' })
  },
  methods: {
    /**
     * 点击按钮点赞/取消点赞触发事件
     */
    btnLikeClick () {
      this.btnLikeLoading = true
      if (this.me.liked) {
        axios.delete('/articles/' + this.id + '/like').finally(() => {
          this.me.liked = false
          setTimeout(() => {
            this.btnLikeLoading = false
          }, 500)
        })
      } else {
        axios.post('/articles/' + this.id + '/like').finally(() => {
          this.me.liked = true
          // this.btnLikeLoading = false
          setTimeout(() => {
            this.btnLikeLoading = false
          }, 500)
        })
      }
    },
    /**
     * 删除文章
     */
    deleteArticle () {
      this.btnDeleteLoading = true
      axios.delete('/articles/' + this.id).finally(() => {
        // axios.delete('http://127.0.0.1:4523/mock/404238/articles/' + this.id).finally(() => {
        setTimeout(() => {
          this.btnDeleteLoading = false
          this.hasDeleted = true
        }, 500)
      })
    }
  }
}
</script>

<style scoped>

</style>
