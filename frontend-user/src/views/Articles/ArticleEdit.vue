<script setup>
import MdEditor         from 'md-editor-v3';
import 'md-editor-v3/lib/style.css';
import {UploadOutlined} from "@ant-design/icons-vue";</script>
<template>
  <div
      v-show="isAuthor"
      class="body"
  >
    <!--标题区域-->
    <h1 style="margin-left: 8vw;margin-top: 20px">
      文章创作中心
    </h1>

    <!--这是一条分割线-->
    <a-divider/>

    <!--文章封面-->
    <a-row
        :gutter="12"
        align="middle"
        justify="center"
        style="padding-top:10px;padding-bottom:60px;"
        type="flex"
    >
      <a-col span="3">
        <h3>文章封面 </h3>
      </a-col>

      <a-col span="12">
        <a-input v-model:value="article.cover"/>
        <a-upload
            :before-upload="beforeUpload"
            :custom-request="customRequest"
            :show-upload-list="false"
        >
          <a-button :loading="btnCoverLoading">
            <UploadOutlined/>
            更换封面图片
          </a-button>
        </a-upload>
        <img
            :src="article.cover"
            alt="文章目前的封面图片"
            style="width: 100%"
        >
      </a-col>
    </a-row>

    <!--文章名称-->
    <a-row
        :gutter="12"
        align="middle"
        justify="center"
        style="padding-top:10px;padding-bottom:60px;"
        type="flex"
    >
      <a-col span="3">
        <h3>文章名称 </h3>
      </a-col>
      <a-col span="12">
        <a-input
            v-model:value="article.title"
            placeholder="输入文章名称"
        />
      </a-col>
    </a-row>

    <!--文章简介-->
    <a-row
        :gutter="12"
        align="middle"
        justify="center"
        style="padding-top:10px;padding-bottom:60px;"
        type="flex"
    >
      <a-col span="3">
        <h3>文章简介 </h3>
      </a-col>
      <a-col span="12">
        <a-textarea
            v-model:value="article.description"
            :rows="4"
            placeholder="输入文章简介"
        />
      </a-col>
    </a-row>
    <a-row
        :gutter="12"
        align="middle"
        justify="center"
        style="padding-top:10px;padding-bottom:60px;"
        type="flex"
    >
      <a-col span="3">
        <h3> 文章状态</h3>
      </a-col>
      <a-col span="3">

        <a-switch v-model:checked="switchChecked"/>
      </a-col>
      <a-col span="9">
        <h3>{{ switchChecked ? '正常显示' : '暂时屏蔽' }}</h3>
      </a-col>
    </a-row>
    <!--文章内容-->
    <a-row
        align="middle"
        justify="center"
        style="padding-top:10px;padding-bottom:60px;"
        type="flex"
    >
      <!--      TODO:本地存储-->
      <MdEditor
          v-model="article.content"
          :toolbarsExclude="['htmlPreview', 'github']"
          editorClass="width:100%"
          style="width: 100%"
          v-on:onUploadImg="onUploadImg"
      />
    </a-row>

    <a-row
        align="middle"
        justify="center"
        style="padding-top:10px;padding-bottom:10px;"
        type="flex"
    >
      <a-button
          :loading="btnArticleLoading"
          type="primary"
          @click="id?updateArticle():createArticle()"
      >
        {{ id ? "更新文章" : "创建文章" }}
      </a-button>
    </a-row>
  </div>
</template>

<script>
import axios     from 'axios'
import {message} from 'ant-design-vue';
import store     from "../../store";

export default {
  name: 'ArticleEdit',

  beforeRouteEnter(to, from, next) {
    if (to.name === 'ArticleCreate') next();
    else if (to.params.id % 1 === 0) next();
    else next({name: 'NotFound'});
  },
  // props: { articleID: String },
  data() {
    return {
      // 用户输入的文章名称和简介
      article: {
        title: '',
        description: '',
        content: '',
        cover: 'http://dummyimage.com/400x300',
        status: 'NORMAL'
      },
      buttonContent: '创建文章',
      btnArticleLoading: false,
      btnCoverLoading: false,
      isAuthor: false
    };
  },
  computed: {
    /**
     * 在edit文章时，返回int类型的文章id
     */
    id() {
      if (this.$route.name === 'ArticleCreate') return 0;
      return +this.$attrs.id;
    },
    switchChecked: {
      get() {
        return this.article.status === "NORMAL"
      },
      set(value) {
        if (value) {
          this.article.status = "NORMAL"
        } else {
          this.article.status = "BLOCKED"
        }
      }
    }
  },

  watch: {
    $router() {
      this.getArticleDetails();
    }
  },

  created() {
    if (this.$route.name === 'ArticleCreate') this.isAuthor = true;
    else this.getArticleDetails();
  },

  methods: {
    /**
     * 创建文章时的命令操作
     */
    createArticle() {
      this.btnArticleLoading = true;
      axios.post('/v1/articles', {...this.article}).then((res) => {
        message.success('恭喜你，创建成功！');
        this.$router.push({name: 'ArticleDetails', params: {id: res.data.id.toString()}});
      }).finally(() => {
        this.btnArticleLoading = false;
      });
    },

    /**
     * 更新文章时候的命令操作
     */
    updateArticle() {
      this.btnArticleLoading = true;
      axios.put(`/v1/articles/${this.id}`, {
        title: this.article.title,
        description: this.article.description,
        content: this.article.content,
        cover: this.article.cover,
        status: this.article.status
      }).then(() => {
        message.success('文章更新成功！');
        this.$router.push({name: 'ArticleDetails', params: {id: this.id.toString()}});
      }).finally(() => {
        this.btnArticleLoading = false;
      });
    },

    /**
     *  @name getArticleDetails
     *  @brief 获取文章内容
     */
    getArticleDetails() {
      if (this.id) {
        axios.get(`/v1/articles/${this.id}`).then((res) => {
          this.isAuthor = res.data.author.username === store.getters.user.username;
          if (this.isAuthor === false) {
            message.error('你没有权限编辑本文！');
            this.$router.push({name: 'Forbidden'});
          }
          this.article = res.data;
        });
      }
    },

    /**
     * 上传图片
     * @param image 即将上传的文件信息
     */
    async imageUpload(image) {
      let result = ""
      const data = new FormData();
      data.append('file', image);
      await axios({
        url: '/v1/global/files',
        method: 'post',
        data,
        headers: {'Content-Type': 'multipart/form-data'},
      }).then((res) => {
        result = res.data.uri;
        message.success('成功上传啦~');
      });
      console.log(result)
      return result
    },

    /**
     * 自定义上传文件请求
     * @param data 需要上传文件
     */
    async customRequest(data) {
      this.btnCoverLoading = true;
      this.article.cover   = await this.imageUpload(data.file);
      this.btnCoverLoading = false;

    },

    /**
     * 在上传之前检查即将上传的文件
     * @param file 即将上传的文件
     */
    beforeUpload(file) {
      const isJpgOrPng = file.type === 'image/jpeg' || file.type === 'image/png';
      if (!isJpgOrPng) {
        message.error('仅支持上传jpg或png文件!');
      }
      const isLt2M = file.size / 1024 / 1024 < 2;
      if (!isLt2M) {
        message.error('上传的图片大小不超过2MB!');
      }
      return isJpgOrPng && isLt2M;
    },

    async onUploadImg(files, callback) {
      console.log(files)
      let back = Array.from(files).map((item) => this.imageUpload(item))
      Promise.all(back).then(callback)
    },
  }
}
</script>

<style scoped>
.body {
  background: white;
  width: 90%;
  display: flex;
  flex-direction: column;
  justify-content: center;
}
</style>
