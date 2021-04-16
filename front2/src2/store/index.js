import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    request_url: "http://localhost:8090/",
    msg_get_content: {
      text: null,
      name: null,
      createdDate: "2017-01-2",
      id: null
    }
  },
  mutations: {
    set_content (state, id, name, text) {
      state.msg_get_content.id = id
      state.msg_get_content.name = name
      state.msg_get_content.text = text
    }
  },
  actions: {
  },
  modules: {
  },
  computed: {
/*    msg_get_content: {
      get(){
        return this.$store.state.msg_get_content;
      },
      set(id, name, text){
          this.$store.state.msg_get_content.text = text;
          this.$store.state.msg_get_content.id = id;
          this.$store.state.msg_get_content.name = name;
      }
    }*/

  }
})
