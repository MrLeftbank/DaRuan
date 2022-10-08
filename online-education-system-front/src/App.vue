<template>
  <div id="app" class="app">
    <el-backtop class="backTop">
      UP
    </el-backtop>
    <router-view />
  </div>
</template>

<script>
export default {
  name: 'App',
  components: {
  },

}
</script>

<style scoped>
.app {
  word-break: break-all;
  word-wrap: break-word;
}

.backTop {
  color: #68cb9b;
  background-color: #f2f5f6;
  box-shadow: 0 0 6px rgba(0, 0, 0, .12);
  text-align: center;
  line-height: 40px;
}

/* 
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
} */
</style>

<style>
* {
  margin: 0;
  padding: 0;
}

body {
  background-color: #FaFaFa;
}
</style>