#include "Sphere.hpp"

using namespace rt;

Intersection Sphere::getIntersection(const Line& line,
                                     double minDist,
                                     double maxDist)
{

  double ax = line.dx().x();
  double ay = line.dx().y();
  double az = line.dx().z();
  double bx = line.x0().x();
  double by = line.x0().y();
  double bz = line.x0().z();
  double cx = (*this).center().x();
  double cy = (*this).center().y();
  double cz = (*this).center().z();
  double r = (*this).radius();
  double p1 = 0;
  double p2 = 0;

  double A = pow(ax,2) + pow(ay,2) + pow(az,2);
  double B = 2*ax*bx + 2*ay*by + 2*az*bz - 2*ax*cx - 2*ay*cy- 2*az*cz;
  double C = pow(bx,2) + pow(by,2) + pow(bz,2) + pow(cx,2) + pow(cy,2) +
    pow(cz,2) - 2*bx*cx - 2*by*cy - 2*bz*cz - pow(r,2);

  double delta = pow(B, 2) - 4 * A * C;
  Intersection in;

  if (delta<0)
    {
      in = Intersection(false, *this, line, 0);
    }
  else if (delta == 0)
    {
      p1 = (-B + sqrt(delta)) / 2 * A;
      in = Intersection(true, *this, line, p1);
    }
  else
    {
      p1=(-B + sqrt(delta)) / 2 * A;
      p2=(-B - sqrt(delta)) / 2 * A;

      if (p1 > p2 && p1 >= minDist && p1 <= maxDist)
        {
          in = Intersection(true, *this, line, p2);
        }
      else if (p2 >= p1 && p2 >= minDist && p2 <= maxDist)
        {
          in = Intersection(true, *this, line, p1);
        }
    }
  return in;
}
