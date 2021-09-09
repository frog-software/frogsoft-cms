<script setup>
import ArticleList from '../../components/Articles/ArticleList.vue';
</script>

<template>
  <div class="body">
    <a-spin
        :delay="500"
        :spinning="spinning"
    >
      <ArticleList
          :page-size="8"
      />
    </a-spin>
  </div>
</template>

<script>
export default {
  name: 'Articles',
  data() {
    return {
      spinning: false,
    };
  }
};
</script>

<style scoped>
.body {
  display: flex;
  flex-direction: column;
  justify-content: center;
  background: white;
  padding: 40px;
}
</style>
