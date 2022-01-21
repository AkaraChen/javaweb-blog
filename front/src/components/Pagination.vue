<template>
  <router-view :to="''">上一页</router-view>
  <router-view>上一页</router-view>
</template>

<script>
import axios from "axios";
import {useStore} from "vuex";
import {computed} from "vue";
import router from "@/router";

export default {
  name: "Pagination",
  computed: {},
  props: {
    identify: String,
    url: String,
    name: String,
    size: Number,
  },
  setup(props) {
    const baseBackend = computed(() => useStore().state.baseBackend)
    const count = ref(0)
    axios.get(baseBackend + '/category/count?category=' + props.identify).then(response => {
      count.value = response.data.count
    })
    const pageCount = count.value / props.size
    return {pageCount}
  },
  methods: {
    prev() {
      if (this.route.params.page <= 1) {
        this.$route.params.page -= 1
      }
    }
  }
}
</script>

<style scoped>

</style>