#include "Sphere.hpp"

using namespace rt;

Intersection Sphere::getIntersection(const Line& line, 
                                     double minDist,
                                     double maxDist) {

 
  
  Intersection in;// = *(new Intersection());
  // ADD CODE HERE
  //this - current sphere
 
  Vector p1 = Vector (line.dx());
  Vector p0 = Vector(line.x0());

  
  double dx = p1.x() - p0.x();
  double dy = p1.y() - p0.y();
  double dz = p1.z() - p0.z();

  double a = dx*dx + dy*dy + dz*dz;
  double b = 2*dx*(p0.x() - this->_center.x()) + 2*dy*(p0.y() - this->_center.y()) + 2*dz*(p0.z() - this->_center.z());
  double c = this->_center.x()*this->_center.x() + this->_center.y()*this->_center.y() + this->_center.z()*this->_center.z() + p0.x()*p0.x() + p0.y()*p0.y() + p0.z()+p0.z() - 2*(this->_center.x()*p0.x() + this->_center.y()*p0.y() + this->_center.z()* p0.z()) - this->_radius*this->_radius;

 double delta = b*b - 4*a*c;
  if (delta < 0 ){
	  in = *(new Intersection());
	  return in;
  /*} else if (delta == 0){
	 double x =  (-b + sqrt(delta)) / 2*a;
	 in = *(new Intersection(true,*this, line, x));*/
  } else {
	double x1 =  (-b + sqrt(delta)) / 2*a;
	double x2 =  (-b - sqrt(delta)) / 2*a;
	if (x2< x1){
		x1=x2;
	}

	
	 in = *(new Intersection(true,*this , line, x1));
  }
  
  return in;
}

