<script setup>
</script>
<template>
  <div>
    <!--新提交的评论-->
    <a-comment v-show="replyTo===parent">
      <template #avatar>
        <a-avatar
            :alt="user.nickname"
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
              :author="item.user.username"
              :content="item.content"
              :datetime="item.time"
          >
            <template #avatar>
              <router-link :to="{name:'UserDetails',params:{id:item.user.id}}">
                <a-avatar :src="item.user.avatar"/>
              </router-link>
            </template>

            <template #actions>
              <a-button
                  v-if="item.user.id===user.id"
                  :disabled="btnCommentSubmitting"
                  type="text"
                  @click="commentDelete(item.id)"
              >
                删除评论
              </a-button>
              <span
                  @click="store.commit('changeReplyTo',item.id)"
              >
                回复评论
              </span>
              <span
                  v-if="replyTo===item.id"
                  @click="store.commit('changeReplyTo',0)"
              >
                取消回复
              </span>
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
import store        from '../../store';

export default {
  // TODO 等待后端评论接口修复完成后更新
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
      this.comments.forEach((item) => {
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
      this.btnCommentSubmitting = true;
      const data                = {
        content: this.newCommentValue,
        parent: this.parent,
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
