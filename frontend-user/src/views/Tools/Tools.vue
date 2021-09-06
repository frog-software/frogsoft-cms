<template>
  <a-card>
    <div
        v-for="item in tools"
        :key="item.id"
    >
      <a-card-grid
          hoverable
          style="width:50%;text-align:center"
      >
        <a-card
            :bordered="false"
            @click="()=>{if(!item.disabled)$router.push({name:item.routerName})}"
        >
          <template #cover>
            <img :src="item.cover">
          </template>

          <template #title>
            <a-button
                :disabled="item.disabled"
                type="link"
            >
              {{ item.name }}
            </a-button>
          </template>

          <a-card-meta :description="item.description"/>
        </a-card>
      </a-card-grid>
    </div>
  </a-card>
</template>

<script>
export default {
  name: 'Tools',
  data() {
    return {
      tools: [
        {
          id: 0,
          cover: 'http://dummyimage.com/400x300',
          name: '条件查字',
          description: '根据声母、韵母、声调筛选符合要求的单字',
          routerName: 'Conditions',
          disabled: false,
        },
        {
          id: 1,
          cover: 'http://dummyimage.com/400x300',
          name: '方言词典',
          description: '提供方言词语查询',
          routerName: 'wordDict',
          disabled: false,
        },
        {
          id: 2,
          cover: 'http://dummyimage.com/400x300',
          name: '方言语音识别工具',
          description: '努力研发中',
          routerName: 'Voice',
          disabled: true,
        },
      ],
    };
  },
  computed: {
    pagination() {
      return {
        pageSize: this.pageSize,
        total: this.tools.length,
      };
    },
  },
};
</script>

<style scoped>

</style>
