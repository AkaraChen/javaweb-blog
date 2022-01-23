<template>
  <div class="ad-bar" v-for="item in meta">
    <a :href="item.link" target="_blank">
      <img width="240" :src="item.pic" alt="">
    </a>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "Sidebar",
  data() {
    return {
      meta: ''
    }
  },
  created() {
    axios.get(this.$store.state.baseBackend + "/setting/check?name=sidebar").then(response => {
      this.meta = response.data
    })
  }
}
</script>

<style scoped>
img {
  display: block;
  margin-bottom: 10px;
}
</style>