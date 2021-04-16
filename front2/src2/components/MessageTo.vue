<template>
  <div class="MessageTo">
    <p class="authorName">{{author}}</p>
    <div class="text"><p>{{msg}}</p></div>
    <p class="date">{{date}}</p>
  </div>
</template>

<script>


export default {
  name: "MessageTo",
  props: [
    'msg',
     'author',
      'date'
  ]
}
</script>

<style scoped>
.MessageTo{

  width: 40%;
  height: 55px;
  border-radius: 5px;
  background-color: lightgray;
  margin: 10px 10px 10px 60%;
}
.MessageTo .authorName{
  color: rgba(0, 0,0,0.4);
  width: 200px;
  margin-left: -70px;
  margin-top: 0;
}
.MessageTo .text{
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  margin-top: -35px;
}
.MessageTo .date{
  color: rgba(0, 0,0,0.4);
  width: 200px;
  margin-left: -10px;
  margin-top: -20px;
}
</style>

