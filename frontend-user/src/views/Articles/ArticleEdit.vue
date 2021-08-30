<template>
  <div v-show="isAuthor" class="body">
    <!--标题区域-->
    <h1 style="margin-left: 8vw;margin-top: 20px"> 文章创作中心 </h1>

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
        <a-input v-model="article.cover"></a-input>
        <a-upload
          :before-upload="beforeUpload"
          :customRequest="customRequest"
          :show-upload-list="false"
        >
          <a-button :loading="btnCoverLoading">
            <a-icon type="upload"/>
            更换封面图片
          </a-button>
        </a-upload>
        <img :src="article.cover" alt="文章目前的封面图片" style="width: 100%"/>
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
        <a-input v-model="article.title" placeholder="输入文章名称"/>
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
        <a-textarea v-model="article.description" :rows="4" placeholder="输入文章简介"/>
      </a-col>
    </a-row>

    <!--文章内容-->
    <a-row
      align="middle"
      justify="center"
      type="flex"
    >
      <mavon-editor
        ref="md"
        v-model="article.content"
        style="z-index: auto"
        @imgAdd="$imgAdd"
        @imgDel="$imgDel"
      />

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
import axios from 'axios'
import { mavonEditor } from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'

export default {
  name: 'ArticleEdit',
  // props: { articleID: String },
  data () {
    return {
      // 用户输入的文章名称和简介
      article: {
        title: '',
        description: '',
        content: '',
        cover: 'http://dummyimage.com/400x300'
      },
      buttonContent: '创建文章',
      btnArticleLoading: false,
      btnCoverLoading: false,
      isAuthor: false
    }
  },
  components: {
    mavonEditor
  },
  computed: {
    /**
     * 在edit文章时，返回int类型的文章id
     */
    id () {
      if (this.$route.name === 'ArticleCreate') return 0
      else return +this.$attrs.id
    }
  },

  methods: {
    /**
     * 创建文章时的命令操作
     */
    createArticle () {
      this.btnArticleLoading = true
      axios.post('/articles', Object.assign({}, this.article)).then(res => {
        this.$message.success('恭喜你，创建成功！')
        this.$router.push({ name: 'ArticleDetails', params: { id: res.data.id.toString() } })
      }).catch(err => {
        this.$message.destroy()
        switch (err.response.status) {
          case 401: {
            this.$message.error('登录状态无效！请重新登录！')
            break
          }
          case 400: {
            this.$message.error('请完成所有内容！')
            break
          }
          case 500: {
            this.$message.error('服务器错误！请联系管理员！')
            this.$message.error('错误内容:' + err.response.data.msg)
            break
          }
        }
      }).finally(() => {
        this.btnArticleLoading = false
      })
    },

    /**
     * 更新文章时候的命令操作
     */
    updateArticle () {
      this.btnArticleLoading = true
      axios.put('/articles/' + this.id, {
        article: {
          title: this.article.title,
          description: this.article.description,
          content: this.article.content,
          cover: this.article.cover
        }
      }).then(res => {
        this.$message.success('文章更新成功！')
        this.$router.push({ name: 'ArticleDetails', params: { id: this.id.toString() } })
      }).catch(err => {
        this.$message.destroy()
        switch (err.response.status) {
          case 401: {
            this.$message.error('登录状态异常！请重新登录后再试！')
            break
          }
          default: {
            this.$message.error(err.toString())
          }
        }
      }).finally(() => {
        this.btnArticleLoading = false
      })
    },

    /**
     *  @name getArticleDetails
     *  @brief 获取文章内容
     */
    getArticleDetails () {
      if (this.id) {
        axios.get('/articles/' + this.id).then(res => {
          this.isAuthor = res.data.me.is_author
          if (this.isAuthor === false) {
            this.$message.error('你没有权限编辑本文！')
            this.$router.push({ name: 'Forbidden' })
          }
          this.article = res.data.article
        })
      }
    },

    /**
     * mavonEditor 绑定@imgAdd event
     * @param pos markdown编辑器中图片的位置
     * @param $file 用户上传的图片文件
     */
    $imgAdd (pos, $file) {
      this.imageUpload($file).then(url => {
        this.$refs.md.$img2Url(pos, url)
      })
    },

    /**
     * mavonEditor 绑定@imgDel event
     * @param pos markdown编辑器中图片的位置
     */
    $imgDel (pos) {
      axios.delete('/website/files', { data: { url: pos[0] } }).then(res => {
        this.$message.success('成功删除图片' + pos[1].name)
      }).catch(err => {
        this.$message.destroy()
        console.log(pos[0])
        console.log(pos[1])
        console.log(err.response)
        switch (err.response.status) {
          case 401: {
            this.$message.error('无权限：请检查您的登录状态')
            break
          }
          case 404: {
            this.$message.warn('图片不存在！已忽略本次操作')
            break
          }
          default: {
            this.$message.error(err.toString())
          }
        }
      })
    },

    /**
     * 上传图片
     * @param file 即将上传的图片文件
     */
    async imageUpload (file) {
      return new Promise(resolve => {
        var formdata = new FormData()
        formdata.append('file', file)
        axios({
          url: '/website/files',
          method: 'post',
          data: formdata,
          headers: { 'Content-Type': 'multipart/form-data' }
        }).then((res) => {
          resolve(res.data.url)
          this.$message.success('成功上传啦~')
        }).catch(err => {
          this.$message.destroy()
          switch (err.response.status) {
            case 401: {
              this.$message.error('无权限：请检查您的登录状态')
              break
            }
            default: {
              this.$message.error(err.toString())
            }
          }
        })
      })
    },

    /**
     * 自定义上传文件请求
     * @param data 需要上传文件
     */
    customRequest (data) {
      this.btnCoverLoading = true
      this.imageUpload(data.file).then(url => {
        this.article.cover = url
        this.btnCoverLoading = false
      })
    },
    /**
     * 在上传之前检查即将上传的文件
     * @param file 即将上传的文件
     */
    beforeUpload (file) {
      const isJpgOrPng = file.type === 'image/jpeg' || file.type === 'image/png'
      if (!isJpgOrPng) {
        this.$message.error('仅支持上传jpg或png文件!')
      }
      const isLt2M = file.size / 1024 / 1024 < 2
      if (!isLt2M) {
        this.$message.error('上传的图片大小不超过2MB!')
      }
      return isJpgOrPng && isLt2M
    }

  },

  created () {
    if (this.$route.name === 'ArticleCreate') this.isAuthor = true
    else this.getArticleDetails()
  },

  watch: {
    $router () {
      this.getArticleDetails()
    }
  },

  beforeRouteEnter (to, from, next) {
    if (to.name === 'ArticleCreate') next()
    else if (to.params.id % 1 === 0) next()
    else next({ name: 'NotFound' })
  }
}
</script>

<style scoped>
.body {
  background: white;
  width: 80%;
  display: flex;
  flex-direction: column;
  justify-content: center;
}
</style>
