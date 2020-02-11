var gulp = require('gulp');
var less = require('gulp-less');
var uglify = require('gulp-uglify');
var rename = require('gulp-rename');
var concat = require('gulp-concat');
var minifycss = require('gulp-minify-css');
var shell = require('shell');
var path = require('path');
var autoprefixer = require('gulp-autoprefixer');
var jshint = require('gulp-jshint');
var stylish = require('jshint-stylish')

var src = {
  less: './src/less/**/*.less',
  js: './src/scripts/**/*.js',
  img: './src/img',
  css: './src/css'
};

var dist = {
  css: './dist/css',
  js: './dist/scripts',
  images: './dist/images'
};

/************scripts*************/
gulp.task('lint', function() {
  return gulp.src(src.js)
    .pipe(jshint('.jshintrc'))
    .pipe(jshint.reporter(stylish, {
      verbose: true
    }))
    .pipe(jshint.reporter('fail'));
});

gulp.task('scripts:uglify', function() {
  return gulp.src(src.js)
    .pipe(rename({
      suffix: '.min'
    }))
    .pipe(uglify())
    .pipe(gulp.dest(dist.js))
});

gulp.task('watch:scripts', function() {
  gulp.watch(src.js, ['lint']);
});


/************style***************/
gulp.task('less', function() {
  return gulp.src(src.less)
    .pipe(less())
    .pipe(autoprefixer('last 2 version', 'safari 5', 'ie 7', 'ie 8', 'ie 9', 'opera 12.1', 'ios 6', 'android 4'))
      .pipe(gulp.dest(src.css));
    // .pipe(rename({
    //   suffix: '.min'
    // }))
    // .pipe(minifycss())
    // .pipe(gulp.dest(dist.css));
});

gulp.task('watch:less', function() {
  gulp.watch(src.less, ['less']);
});

/************default**************/

gulp.task('default', ['less', 'watch:less']);
