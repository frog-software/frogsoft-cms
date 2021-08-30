<template>
  <a-result
    status="403"
    title="403"
    sub-title="对不起，你没有访问该页面的权限！">
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
  name: 'Forbidden'
}
</script>

<style scoped>

</style>
