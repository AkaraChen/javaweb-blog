<template>
  <input type="text" v-model="name" placeholder="用户名">
  <input type="password" v-model="passwd" placeholder="密码">
  <button @click="post">提交</button>
</template>

<script>
import {useStore} from 'vuex'
import {ref} from "vue";
import axios from "axios";

export default {
  name: "Register",
  setup() {
    const store = useStore()
    const baseBackend = store.state.baseBackend
    const meta = ref(0)
    const name = ref(undefined)
    const passwd = ref(undefined)
    return {baseBackend, meta, name, passwd}
  },
  methods: {
    post() {
      axios({
        method: 'POST',
        url: this.baseBackend + '/user/add',
        params: {
          key: 'adminKey',
          name: this.name,
          password: this.passwd,
        }
      })
    }
  }
}
</script>

<style scoped>

</style>