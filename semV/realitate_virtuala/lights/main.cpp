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

  double lightIntesity = 0.8;

  Vector viewParallel = viewUp^viewDirection;

  viewDirection.normalize();
  viewUp.normalize();
  viewParallel.normalize();

  Image image(imageWidth, imageHeight);

  Color lightAmbient(1, 1, 1);
  Color lightDiffuse(1, 1, 1);
  Color lightSpecular(1, 1, 1);
  Material lightMat(lightAmbient, lightDiffuse, lightSpecular, 1, 1, 1);
  Vector light1(-1000, 1000, 0);

  Color ambient(0.1, 0.1, 0.1);
  Color difuse(0.3, 0.3, 0.3);
  Color specular(1, 1, 1);
  Material sphereMat(ambient, difuse, specular, 20, 0.5, 1);

  for (int i=0; i < imageWidth; i++)
    {
      double scaledi = imageToViewPlane (i, imageHeight, viewPlaneHeight);
      for (int j=0; j < imageHeight; j++)
        {
          double scaledj = imageToViewPlane (j, imageHeight, viewPlaneHeight);

          Vector x1 = viewPoint + 
            viewDirection * viewPlaneDist +
            viewUp * scaledj +
            viewParallel * scaledi;

          Line newLine =  Line(viewPoint, x1, false);
          Intersection in = findFirstIntersection(newLine, frontPlaneDist, backPlaneDist);

          if (in.valid())
            {
              // vector from the intersection point to the light
              Vector T = light - in.vec();
              T.normalize();

              // normal to the surface at the intersecction point
              Vector N = in.vec() - ((Sphere *)&in.geometry())->center();
              N.normalize();

              // vector from the intersection point to the camera
              Vector E = viewPoint - in.vec();
              E.normalize();

              // reflection vector
              Vector R = N * (N * T) * 2 - T;
              R.normalize();

              Color color = sphereMat.ambient() * lightMat.ambient() * in.geometry().color();

              // add diffused light
              if (N * T > 0) {
                color += sphereMat.diffuse() * lightMat.diffuse() * (N * T) * in.geometry().color();
              }

              // add specular light
              if (E * R > 0) {
                color += sphereMat.specular() * lightMat.specular() * pow(E * R,sphereMat.shininess()) * in.geometry().color();
              }

              color *= lightIntesity;
              image.setPixel(i,j,color );
            }
        }
    }

  image.store("scene.ppm");

  for(int i=0; i<geometryCount; i++) {
    delete scene[i];
  }

  return 0;
}
