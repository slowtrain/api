<template>
  <section class="carousel">
    <div class="card">
      <div class="card-body">
        <h1 class="card-title">Open API Platform ? </h1>
        <b-carousel id="carousel1" controls indicators background="#ababab" :interval="4000" v-model="slide1">
          <b-carousel-slide>
            <img slot="img" src="@/assets/images/banner/banner_1.jpg" alt="slide image" />
          </b-carousel-slide>
          <b-carousel-slide>
            <img slot="img" src="@/assets/images/banner/banner_2.jpg" alt="slide image" />
          </b-carousel-slide>
          <b-carousel-slide>
            <img slot="img" src="@/assets/images/banner/banner_3.jpg" alt="slide image" />
          </b-carousel-slide>
          <b-carousel-slide>
            <img slot="img" src="@/assets/images/banner/banner_4.jpg" alt="slide image" />
          </b-carousel-slide>
          <!--
          <b-carousel-slide>
            <img slot="img" src="https://via.placeholder.com/495x310" alt="slide image" />
          </b-carousel-slide>
          -->
        </b-carousel>
      </div>
    </div>
  </section>
</template>

<script>
export default {
  name: "carousel",
  data() {
    return {
      slide1: 0,
      slide2: 0,
      slide3: 0
    };
  }
};
</script>

<style scoped lang="scss">
</style>
