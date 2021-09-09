<script setup>
import store from '../../store'</script>
<template>
  <div>
    <!--新提交的评论-->
    <a-comment v-show="replyTo===parent">
      <template #avatar>
        <a-avatar
            :alt="user.username"
            :src="user.avatar"
        />
      </template>
      <template #content>
        <a-form-item>
          <a-textarea
              v-model:value="newCommentValue"
              :rows="4"
          />
        </a-form-item>
        <a-form-item>
          <a-button
              :disabled="newCommentValue===''"
              :loading="btnCommentSubmitting"
              html-type="submit"
              type="primary"
              @click="commentSubmit(replyTo)"
          >
            评论
          </a-button>
        </a-form-item>
      </template>
    </a-comment>

    <!--现有评论-->
    <a-list
        v-if="filteredComments.length"
        :data-source="filteredComments"
        :header="`${filteredComments.length} ${filteredComments.length > 1 ? 'replies' : 'reply'}`"
        :loading="{spinning:commentsLoading,delay:500}"
        :pagination="{pageSize: pageSize,hideOnSinglePage:true}"
        item-layout="horizontal"
    >
      <template #renderItem="{item}">
        <a-list-item>
          <a-comment
              :author="item.author.username"
              :content="item.content"
              :datetime="item.time"
          >
            <template #avatar>
              <router-link :to="{name:'UserDetails',params:{username:item.author.username}}">
                <a-avatar :src="item.author.avatar||'/avatar.png'"/>
              </router-link>
            </template>

            <template #actions>
              <a-button
                  v-if="item.author.username===user.username"
                  :disabled="btnCommentSubmitting"
                  type="text"
                  @click="commentDelete(item.id)"
                  style="font-size: 12px"
              >
                删除评论
              </a-button>
              <a-button
                  type="text"
                  @click="store.commit('changeReplyTo',item.id)"
                  style="font-size: 12px"
              >
                回复评论
              </a-button>
              <a-button
                  v-if="replyTo===item.id"
                  @click="store.commit('changeReplyTo',0)"
                  style="font-size: 12px"
                  type="text"
              >
                取消回复
              </a-button>
            </template>
            <comment-list
                :id="id"
                :comments="comments"
                :page-size="3"
                :parent="item.id"
            />
          </a-comment>
        </a-list-item>
      </template>
    </a-list>
  </div>
</template>

<script>
import axios        from 'axios';
import {mapGetters} from 'vuex';
import {message}    from 'ant-design-vue';

export default {
  name: 'CommentList',
  props: {
    parent: Number,
    pageSize: Number,
    id: Number
  },
  data() {
    return {
      btnCommentSubmitting: false,
      newCommentValue: '',
      commentsLoading: false,
    };
  },
  computed: {
    ...mapGetters([
      'user',
      'replyTo',
      'comments',
    ]),
    filteredComments() {
      const result = [];
      this.comments?.forEach((item) => {
        if (item.parent === this.parent) {
          result.push(item);
        }
      });
      return result;
    },
  },
  methods: {

    /**
     * 提交评论
     * @param parent 回复的评论的id，若无则为0
     */
    commentSubmit(parent) {
      if (store.getters.loginStatus === false) {
        message.error('登录后才能发表评论哦~')
        return
      }
      let trueParent = parent
      this.comments.forEach(item => {
        if (item.id === parent && item.parent != 0) {
          trueParent = item.parent
          message.info('评论层级太多啦~自动帮你回复到上一级了')
        }
      })
      this.btnCommentSubmitting = true;
      const data                = {
        content: this.newCommentValue,
        parent: trueParent,
      };
      axios.post(`/v1/articles/${this.id}/comments`, data).then(async () => {
        await store.commit('updateComments', this.id);
        this.newCommentValue = '';
        message.success('评论发布成功');
      }).finally(() => {
        setTimeout(() => {
          this.btnCommentSubmitting = false;
          store.commit('changeReplyTo', 0);
        }, 500);
      });
    },
    /**
     * 删除评论
     * @param id 删除的评论的id
     */
    commentDelete(id) {
      this.commentsLoading = true;
      axios.delete(`/v1/articles/${this.id}/comments`, {
        data: {
          id,
        },
      }).then(async () => {
        message.success('成功删除评论');
        await store.commit('updateComments', this.id);
        this.commentsLoading = false;
      });
    },
  },
};
</script>

<style scoped>

</style>
