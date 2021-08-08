angle = random(360);
dx = cos(degtorad(angle));
dy = -sin(degtorad(angle));
image_angle = angle;
spd = irandom_range(6, 10);
pontos++;
show_debug_message(pontos)