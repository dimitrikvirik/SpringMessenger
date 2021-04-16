<template>
  <div id="app">
    <router-view/>
  </div>
</template>
<script>
import M from 'materialize-css'
export default {
  mounted() {
    M.AutoInit(undefined)
  }
}
</script>
<style>
#app{
  width: 100%;
  height: 100%;
  position: absolute;
}

#nav {
  padding: 30px;
}

#nav a {
  font-weight: bold;
  color: #2c3e50;
}

#nav a.router-link-exact-active {
  color: #42b983;
}
</style>
