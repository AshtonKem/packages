(set-env!
  :resource-paths #{"resources"}
  :dependencies '[[cljsjs/boot-cljsjs "0.5.0" :scope "test"]])

(require '[cljsjs.boot-cljsjs.packaging :refer :all])

(def +lib-version+ "3.10.1")
; FIXME: Next release should have build identier
(def +version+ (str +lib-version+ ""))

(task-options!
 pom  {:project     'cljsjs/openlayers
       :version     +version+
       :description "A high-performance, feature-packed library for all your mapping needs"
       :url         "http://openlayers.org/"
       :scm         {:url "https://github.com/cljsjs/packages"}
       :license     {"BSD" "http://opensource.org/licenses/BSD-2-Clause"}})

(deftask package []
  (comp
    (download :url (format "https://github.com/openlayers/ol3/releases/download/v%s/v%s.zip" +lib-version+ +lib-version+)
              :checksum "C050655A3526492C206A9F0ED71851AB"
              :unzip true)
    (download :url (format "https://github.com/openlayers/ol3/archive/v%s.zip" +lib-version+)
              :checksum "BF5D94BF54307468FE329B45A0A44665"
              :unzip true)
    (sift :move {#"^v([\d\.]*)/ol/ol/" "cljsjs/openlayers/development/ol/"
                 #"^v([\d\.]*)/ol\.ext/" "cljsjs/openlayers/development/ol.ext/"
                 #"^v([\d\.]*)/css/ol\.css" "cljsjs/openlayers/common/openlayers.inc.css"
                 #"^ol3-([\d\.]*)/externs/(.*)\.js" "cljsjs/openlayers/common/$2.ext.js"})
    (sift :include #{#"^cljsjs/" #"deps.cljs"})))
