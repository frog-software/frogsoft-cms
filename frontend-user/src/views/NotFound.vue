<template>
  <a-result
    status="404"
    title="404"
    sub-title="对不起，你所访问的页面不存在！">
    <template #extra>
      <router-link :to="{name:'Home'}">
        <a-button type="primary">
          返回主页
        </a-button>
      </router-link>
    </template>
  </a-result>
</template>

<script>
export default {
  name: 'NotFound'
}
</script>

<style scoped>

</style>
