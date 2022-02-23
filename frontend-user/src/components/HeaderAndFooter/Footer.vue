<script setup>
import store from '../../store';
</script>
<template>
  <div class="footer">
    <a-row
        align="middle"
        justify="space-around"
        type="flex"
    >
      <a-col span="3">
        <img
            :src="store.getters.config.footerDto.logo"
            alt="网站页脚Footer"
            width="240"
        >
      </a-col>
      <a-col span="12">
        <h4 style="color: white">
          相关链接：
          <a-button
              href="https://github.com/frog-software"
              type="link"
          >
            Github - frog-software
          </a-button>
          <a-button
              href="https://github.com/frog-software/frogsoft-cms"
              type="link"
          >
            Github - Frogsoft CMS
          </a-button>
        </h4>
        <h4>
          Copyrights © {{ new Date().getFullYear() }}
          <a-button
              herf="https://github.com/frog-software"
              style="padding: 0"
              type="link"
          >
            Frogsoft
          </a-button>
          . All Rights Reserved.
        </h4>
      </a-col>
    </a-row>
  </div>
</template>

<script>

export default {
  name: 'Footer',
};
</script>

<style scoped>
.footer {
  background: rgb(46, 46, 46);
  width: 100%;
  color: white;
}

h4 {
  color: rgb(199, 199, 199);
}

p {
  font-size: 0.1rem;
  margin: auto;
  color: #8b8b8b;
}
</style>
