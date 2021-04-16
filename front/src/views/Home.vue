<template>
  <div>
    <Login v-if="!islogged"/>
    <Chat v-else/>
  </div>
</template>

<script>
import axios from "axios";
import Login from "@/views/Login";
import Chat from "@/views/Chat";
export default {
  name: "Home",
  components: {
    Chat,
    Login
  },
  data() {
    return{
      islogged: true
    }
  },
  mounted() {
    axios.get(this.$store.state.request_url + 'user/joined', {
    })
        .then(function (response) {
          // handle success
          console.log(response);
          if(response.data !== ""){
            this.islogged = true;
          }
        })
        .catch(function (error) {
          // handle error
          console.log(error);
        })
        .then(function () {
          // always executed
        });
  }
}
</script>

<style scoped>

</style>