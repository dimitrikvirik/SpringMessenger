<template>
  <div class="MessageFrom">
    <p class="username">1-{{username}}</p>
    <div class="text"><p>{{msg}}</p></div>
    <p class="date">{{date}}</p>
  </div>
</template>

<script>
export default {
  name: "MessageFrom",
  props: [
      'msg',
      'username',
      'date'
  ]
}
</script>

<style scoped>
.MessageFrom{

  width: 40%;
  height: 55px;
  padding: 20px;
  border-radius: 5px;
  margin-top: 20px;
  background-color: lightblue;
  position: relative;
}
.MessageFrom .username{
  color: rgba(0, 0,0,0.4);
  width: 200px;
  position: absolute;
  top: -20px;
}

.MessageFrom .text{
  display: flex;
  justify-content: center;
  margin-top: -35px;
}
.MessageFrom .date{
  color: rgba(0, 0,0,0.4);
  width: 200px;
  margin-left: -10px;
  margin-top: 0px;
}
</style>