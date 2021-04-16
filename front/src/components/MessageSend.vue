<template>
  <div class="MessageSend">
    <form method="get">
      <label>
        <input type="text" v-model="value">
        <input type="button" value="Send" v-on:click="sendMsg()">
      </label>
    </form>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "MessageSend",
  data(){
    return{
      value: ''
    }
  },
  props: [
    'user'
  ],
  methods: {
    getValue: function (){
      console.log(this.value + " " + this.user);
    },
    sendMsg: function () {
      const vm = this;
      if (vm.value !== '') {
        const params = new URLSearchParams();
        params.append('text', vm.value);
        params.append('name', vm.user)
        axios({
          method: 'post',
          url: this.$store.state.request_url,
          data: params
        }).then(function (response){
          console.log(response);
        });
      }
    },
  }
}
</script>

<style scoped>
.MessageSend{
  width: 500px;
  height: 50px;
  background-color: white;
  box-shadow: black 0px 2px 5px;
  position: fixed;
  bottom : 20px;
}
  .MessageSend form{
    width: 100%;
    height: 50px;
  }
  .MessageSend form input[type="text"]{
    height: 60%;
    margin-top: 5px;
    width: 90%;
    border-radius: 5px;
    border: 1px solid rgba(0, 0, 0, 0.5);
    font-size: 20px;
  }
  .MessageSend form input[type="button"]{
    height: 65%;
    cursor: pointer;
  }



</style>