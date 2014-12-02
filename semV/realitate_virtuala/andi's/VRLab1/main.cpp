#include <cmath>
#include <iostream>
#include <string>

#include "Vector.hpp"
#include "Line.hpp"
#include "Geometry.hpp"
#include "Sphere.hpp"
#include "Image.hpp"
#include "Color.hpp"
#include "Intersection.hpp"
#include "Material.hpp"

#include "Scene.hpp"

using namespace std;
using namespace rt;

double imageToViewPlane(int n, int imgSize, double viewPlaneSize) {
  double u = (double)n*viewPlaneSize/(double)imgSize;
  u -= viewPlaneSize/2;
  return u;
}

const Intersection findFirstIntersection(const Line& ray,
                                         double minDist, double maxDist) {
  Intersection intersection;

  for(int i=0; i<geometryCount; i++) {
    Intersection in = scene[i]->getIntersection(ray, minDist, maxDist);
    if(in.valid()) {
      if(!intersection.valid()) {
        intersection = in;
      }
      else if(in.t() < intersection.t()) {
        intersection = in;
      }
    }
  }

  return intersection;
}

int main() {
  Vector viewPoint(0, 0, 0);
  Vector viewDirection(0, 0, 1);
  Vector viewUp(0, -1, 0);

  double frontPlaneDist = 2;
  double backPlaneDist = 1000;

  double viewPlaneDist = 100;
  double viewPlaneWidth = 200;
  double viewPlaneHeight = 100;

  int imageWidth = 1000;
  int imageHeight = 500;

  Vector viewParallel = viewUp^viewDirection;
  Vector light = *new Vector(900,990,0);

  viewDirection.normalize();
  viewUp.normalize();
  viewParallel.normalize();
  
  Image image(imageWidth, imageHeight);

  for (int i=0;i<imageWidth;i++){
	   double scaledi = imageToViewPlane(i,imageWidth,viewPlaneWidth);
	   for (int j=0;j<imageHeight;j++){
			double scaledj = imageToViewPlane(j,imageHeight,viewPlaneHeight);
			Vector viewPlaneMiddle = viewDirection * viewPlaneDist;
			//Vector pixelPositionDir = viewUp +viewParallel;
			//sqrt((x2-x1)^2+(y2-y1)^2)
		//	double* temp = &viewPlaneMiddle.x();
		//	double x2 = *temp;

			double x2 = *(&viewPlaneMiddle.x());
			double y2 = *(&viewPlaneMiddle.y());
			Vector scaledViewUp = viewUp*scaledj;
			Vector scaledViewParallel = viewParallel*scaledi;
		//	int pixelPosition = sqrt( (scaledi-x2)*(scaledi-x2) + (scaledj-y2)*(scaledj-y2));
			Vector rayThough = viewPlaneMiddle + scaledViewUp + scaledViewParallel+ viewPoint;

			Line *ray = new Line(viewPoint,rayThough,false);

			Intersection intersection = findFirstIntersection(*ray,frontPlaneDist,backPlaneDist);

			

			//Color *c = new Color(252, 0 , 0);
			if (intersection.valid()){
				Vector v = rayThough;
				//v.normalize();
				Vector e = viewPoint - v ;
				e.normalize();
				Vector n = v - ((Sphere&)(intersection.geometry())).center();
				n.normalize();
				Vector t = light - v;
				t.normalize();
				Vector r = n * (n*t)*2 -t;
				r.normalize();
				Color lightAmbient = Color(0,0, 0);
				Color lightDiffuse = Color(0, 1 , 1);
				Color lightSpecular = Color( 1, 1, 1);
				double lightIntensity = 0.4;
				Material material = *new Material(lightAmbient,lightDiffuse,lightSpecular,lightIntensity,1,1);
				

				Color color = intersection.geometry().color();
				color += material.ambient()*lightAmbient;
				if (n*t > 0){
					color +=  material.diffuse()*lightDiffuse*(n*t);
				}
				if (e*r > 0){
					color += material.specular()* lightSpecular * pow(e*r, material.shininess());
				}
				color *= lightIntensity;
				//image.setPixel(i,j,intersection.geometry().color());
				image.setPixel(i,j,color);
			}
				image.setPixel(100,100,*new Color(256,0,0));
	}
  }

  image.store("scene.ppm");

  for(int i=0; i<geometryCount; i++) {
    delete scene[i];
  }

  return 0;
}
