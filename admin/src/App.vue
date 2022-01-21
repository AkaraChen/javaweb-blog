<template>
  <Navbar/>
  <div class="container">
    <router-view/>
  </div>
</template>

<script>
import Navbar from '@/components/Navbar'

export default {
  components: {Navbar}
}
</script>

<style>
.container {
  margin-top: 10px;
}
</style>