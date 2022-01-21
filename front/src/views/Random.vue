<template>
  <div v-for="item in meta">
    <a :href="'/#/post/'+item.id">{{ item.title }}</a>
  </div>
</template>

<script>
import axios from "axios";
import {useStore} from "vuex";
import {ref} from "vue";

export default {
  name: "Random",
  setup() {
    const store = useStore()
    const baseBackend = store.state.baseBackend
    const meta = ref(0)
    axios.get(baseBackend + '/post/random').then(response => {
      meta.value = response.data
    })
    return {meta}
  },
}
</script>

<style scoped>

</style>