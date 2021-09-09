<script setup>
import {EyeOutlined, LikeOutlined, StarOutlined} from '@ant-design/icons-vue';
import CommentList                               from "../../components/Articles/CommentList.vue";
import MdEditor                                  from 'md-editor-v3';
import 'md-editor-v3/lib/style.css';</script>
<template>
  <a-spin
      v-if="hasDeleted===false"
      :delay="500"
      :spinning="spinning"
  >
    <a-row
        :gutter="20"
        justify="center"
        type="flex"
    >
      <!--文章主体部分-->
      <a-col span="17">
        <!--标题、封面、简介-->
        <a-card>
          <template #title>
            <h1> {{ article.title }} </h1>
          </template>
          <!--TODO 删除接口检验-->
          <template #extra v-if="me.is_author">
            <a-button
                :loading="btnDeleteLoading"
                type="primary"
                @click="deleteArticle"
            >
              删除
            </a-button>
            <router-link :to="{name:'ArticleEdit',params:{id: id}}">
              <a-button type="primary">
                编辑
              </a-button>
            </router-link>

          </template>

          <template #cover>
            <img
                :alt="'这里本是文章的封面，其地址为'+article.cover+',但是显示不出来了'"
                :src="article.cover"
            >
          </template>

          <p> 文章简介： {{ article.description }} </p>
        </a-card>

        <!---文章主体部分--->
        <a-card>
          <MdEditor
              v-model="article.content"
              :previewOnly="true"
          ></MdEditor>
        </a-card>

        <!--评论区-->
        <a-card>
          <template #title>
            <h3> 评论区 </h3>
          </template>
          <a-spin
              :delay="500"
              :spinning="commentsLoading"
          >
            <CommentList
                :id="id?+id:1"
                :page-size="8"
                :parent="0"
            />
          </a-spin>
        </a-card>
      </a-col>

      <!--文章的附加信息-->
      <a-col span="7">
        <!-- 文章的附加信息-->
        <a-card style="margin-top: 16px" title="文章信息">
          <router-link
              :to="{name:'UserDetails',params:{username: article?.author?.username}}"
          >
            <a-card-meta :title="article.author.username">
              <template #avatar>
                <a-avatar :src="article.author.avatar||'/avatar.png'"/>
              </template>
            </a-card-meta>
          </router-link>
          <br>
          <h3> 发布时间:<br>&nbsp;&nbsp;&nbsp;&nbsp;{{ article.publishDate }} </h3>
          <h3> 最近更新:<br>&nbsp;&nbsp;&nbsp;&nbsp;{{ article.updateDate }} </h3>
          <h3>
            <EyeOutlined/>
            阅读量：{{ article.views }}
          </h3>
          <h3>
            <StarOutlined/>
            收藏量：{{ article.favorites }}
          </h3>
          <h3>
            <LikeOutlined/>
            点赞量：{{ article.likes }}
          </h3>

        </a-card>
        <a-card style="margin-top: 16px" title="文章操作">
          <a-button
              style="margin-bottom:8px"
              v-if="store.getters.loginStatus"
              :loading="btnFavoriteLoading"
              type="primary"
              @click="btnFavoriteClick"
          >
            {{ me.favorited ? "取消" : "" }}收藏
          </a-button>
          <br>
          <a-button
              style="margin-top:8px"
              v-if="store.getters.loginStatus"
              :loading="btnLikeLoading"
              type="primary"
              @click="btnLikeClick"
          >
            {{ me.liked ? "取消" : "" }}点赞
          </a-button>
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
import axios     from 'axios';
import {message} from 'ant-design-vue';
import store     from '../../store';

export default {
  name: 'ArticleDetails',
  beforeRouteEnter(to, from, next) {
    if (+to.params.id % 1 === 0) {
      next()
    } else
      next({name: 'NotFound'});
  },
  props: {id: String},
  data() {
    return {
      spinning: true,
      btnLikeLoading: false,
      btnFavoriteLoading: false,
      btnDeleteLoading: false,
      hasDeleted: false, // 文章是否被删除
      article: {
        id: 0,
        author: {
          username: 'username',
          email: 'edialect@edialect.top',
          is_admin: false
        },
        likes: 0,
        views: 0,
        favorites: 0,
        title: 'title',
        description: 'description',
        content: 'content',
        cover: 'http://dummyimage.com/160x90',
        status: "NORMAL",
        publishDate: "2021-09-01 00:00:00",
        updateDate: "2021-09-01 00:00:00",
      },
      me: {
        liked: false,
        is_author: false,
        favorited: false,
        authored: false,
      },
    };
  },
  computed: {
    commentsLoading() {
      return store.getters.commentsLoading;
    },
  },
  created() {
    axios.get(`/v1/articles/${this.id}`).then(async (res) => {
      this.article                 = {...res.data}
      this.article.author.is_admin = res.data.author.roles.includes("ROLE_ADMIN")
      this.me.is_author            = res.data.author.username === store.getters.user.username
      this.me.liked                = res.data.liked || false
      this.me.favorited            = res.data.favorited
      this.me.authored             = res.data.authored
      store.commit('updateComments', this.id);
    }).catch((err) => {
      this.$router.replace({name: 'NotFound'});
    }).finally(() => {
      this.spinning = false;
    });
  },
  methods: {
    /**
     * 点击按钮点赞/取消点赞触发事件
     */
    btnLikeClick() {
      this.btnLikeLoading = true;
      if (this.me.liked) {
        axios.delete(`/v1/articles/${this.id}/like`).then(() => {
          this.me.liked = false;
          setTimeout(() => {
            this.article.likes -= 1;
            this.btnLikeLoading = false;
          }, 500);
        });
      } else {
        axios.post(`/v1/articles/${this.id}/like`).then(() => {
          this.me.liked = true;
          setTimeout(() => {
            this.article.likes += 1;
            this.btnLikeLoading = false;
          }, 500);
        });
      }
    },
    /**
     * 点击按钮点赞/取消点赞触发事件
     */
    btnFavoriteClick() {
      this.btnFavoriteLoading = true;
      if (this.me.favorited) {
        axios.delete(`/v1/articles/${this.id}/favor`).then(() => {
          this.me.favorited = false;
          setTimeout(() => {
            this.article.favorites -= 1
            this.btnFavoriteLoading = false;
          }, 500);
        });
      } else {
        axios.post(`/v1/articles/${this.id}/favor`).then(() => {
          this.me.favorited = true;
          setTimeout(() => {
            this.article.favorites += 1
            this.btnFavoriteLoading = false;
          }, 500);
        });
      }
    },
    /**
     * 删除文章
     */
    deleteArticle() {
      this.btnDeleteLoading = true;
      axios.delete(`/v1/articles/${this.id}`).finally(() => {
        // axios.delete('/v1http://127.0.0.1:4523/mock/404238/articles/' + this.id).finally(() => {
        setTimeout(() => {
          this.btnDeleteLoading = false;
          this.hasDeleted       = true;
        }, 500);
      });
    },
  },
};
</script>

<style scoped>

</style>
