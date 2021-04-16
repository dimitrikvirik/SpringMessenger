<template>
  <div class="home">
    <!--Login form-->
    <div id="login" v-if="dologin">
    <div class="row">
      <form class="col s12">
        <div class="row">
          <div class="input-field col s12">
            <input id="login_username" type="text" class="validate" v-bind:class="usernameError" v-model="userForm.username">
            <label for="login_username">Username</label>
            <span class="helper-text" v-bind:data-error="usernameErrorMsg"></span>
          </div>
        </div>
        <div class="row">
          <div class="input-field col s12">
            <input id="login_password" type="password" class="validate" v-model="userForm.password">
            <label for="login_password">Password</label>
          </div>
        </div>
        <button class="btn waves-effect waves-light"  v-on:click="login" type="button" name="action">Login
          <i class="material-icons right">send</i>
        </button>
        <button class="btn waves-effect waves-light"  v-on:click="show_reg"  type="button" name="action">Registration
          <i class="material-icons right">send</i>
        </button>
      </form>
    </div>
    </div>
     <!--Registration form-->
      <div id="reg" v-if="doreg">

        <div class="row">
          <form class="col s12">
            <div class="row">
              <div class="input-field col s12">
                <input id="reg_username" type="text" class="validate" v-bind:class="usernameError" v-model="regForm.username">
                <label for="reg_username">Username</label>
                <span class="helper-text" v-bind:data-error="usernameErrorMsg"></span>
              </div>
            </div>
            <div class="row">
              <div class="input-field col s12">
                <input id="reg_firstname" type="text" class="validate" v-model="regForm.firstname">
                <label for="reg_firstname">Firstname</label>
              </div>
            </div>
            <div class="row">
              <div class="input-field col s12">
                <input id="reg_lastname" type="text" class="validate" v-model="regForm.lastname">
                <label for="reg_lastname">Lastname</label>
              </div>
            </div>
            <div class="row">
              <div class="input-field col s12">
                <input id="reg_password" type="password" class="validate" v-model="regForm.password">
                <label for="reg_password">Password</label>
              </div>
            </div>
            <button class="btn waves-effect waves-light"  v-on:click="reg"  type="button" name="action">Registration
              <i class="material-icons right">send</i>
            </button>
            <button class="btn waves-effect waves-light"  v-on:click="show_login" type="button" name="action">Login
              <i class="material-icons right">send</i>
            </button>
          </form>
        </div>
    </div>
    </div>
</template>

<script>
// @ is an alias to /src


import axios from "axios";

export default {
  name: 'Home',
  data(){
    return{
      dologin: true,
      doreg: false,
      regForm: {
        firstname: "",
        lastname: "",
        username: "",
        password: ""
      },
      userForm: {
        username: "",
        password: ""
      },
      usernameError: "",
      usernameErrorMsg: ""
    }
  },
  methods: {
    show_login(){
      this.dologin = true
      this.doreg = false
    },
   show_reg(){
     this.dologin = false
     this.doreg = true
    },
    login(){
      let vm = this;
      vm.usernameError = ""
      axios.post(this.$store.state.request_url+'user/login', {
        username: this.userForm.username,
        password: this.userForm.password
      })
          .then((response) => {
            console.log(response);
            if(response.status === 200){
              window.location.href = "/";
            }
          }).catch(function (error) {
        vm.usernameError = "invalid"
        vm.usernameErrorMsg = error.response.data.message
      });
    },
    reg(){
     let vm = this;
      vm.usernameError = ""
      axios.post(this.$store.state.request_url+'user/reg', {
        firstname: this.regForm.firstname,
        lastname: this.regForm.lastname,
        username: this.regForm.username,
        password: this.regForm.password
      })
          .then((response) => {
            console.log(response.data);
            if(response.status === 200){
              window.location.href = "/";
            }
          }).catch(function (error) {
          vm.usernameError = "invalid"
          vm.usernameErrorMsg = error.response.data.message
      });
    }

  }
}
</script>
<style scoped>
  .home{
    display: flex;
    align-items: center;
    justify-content: center;
    position: absolute;
    width: 100%;
    height: 100%;
  }
  #login, #reg{
    border: 3px dashed black;
    width: 500px;
    height: 250px;
  }
  #reg{
    height: 500px;
  }

  #login button, #reg button{
    margin-top: -40px;
    margin-right: 30px;
  }
</style>