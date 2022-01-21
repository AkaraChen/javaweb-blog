<template>
  <Navbar/>
  <div class="my-container row">
    <div class="row">
      <div class="col-md-9">
        <router-view :key="$route.params.page"/>
      </div>
      <div class="col-md-3 side">
        <Sidebar/>
      </div>
    </div>
  </div>
</template>>

<script>
import Navbar from "@/components/Navbar";
import Sidebar from "@/components/Sidebar";

export default {
  components: {Navbar, Sidebar}
}
</script>

<style>
@media (min-width: 766px) {
  .my-container {
    width: 60%;
    margin: auto;
  }
}

@media (max-width: 766px) {
  .my-container {
    justify-content: center !important;
  }

  .side {
    display: none;
  }
}

* {
  user-select: none;
}
</style>
