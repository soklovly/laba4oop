import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class Main {
    public static void main(String[] args) {
        JFrame fr = new JFrame("рисуночек");
        fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Создаем экземпляр пользовательской панели
        BouncingCirclePanel pan = new BouncingCirclePanel();
        pan.setPreferredSize(new Dimension(500, 500));
        fr.add(pan);
        fr.pack();
        fr.setVisible(true);
        // Таймер для обновления движения окружности
        Timer tm = new Timer(20, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pan.updateCircle(); // Теперь метод доступен
            }
        });
        tm.start();
    }
}
// Класс пользовательской панели для отрисовки и обновления окружности
class BouncingCirclePanel extends JPanel {
    private int x = 150; // Начальные координаты окружности
    private int y = 150;
    private int radius = 50; // Радиус окружности
    private int dx = 4; // Скорость по оси X
    private int dy = 3; // Скорость по оси Y
    private int minRadius = 30; // Минимальный радиус
    private int maxRadius = 50; // Максимальный радиус
    private boolean isCompressing = false; // Флаг для сжатия
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D gr = (Graphics2D) g;
        gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // Рисуем окружность
        gr.setColor(Color.BLUE);
        gr.fillOval(x - radius, y - radius, 2 * radius, 2 * radius);
    }
    public void updateCircle() {
        x += dx;
        y += dy;
        // Проверяем столкновение с границами панели
        if (x - radius <= 0 || x + radius >= getWidth()) {
            dx = -dx; // Меняем направление по оси X
            triggerCompression();
        }
        if (y - radius <= 0 || y + radius >= getHeight()) {
            dy = -dy; // Меняем направление по оси Y
            triggerCompression();
        }
        // Эффект упругого сжатия
        if (isCompressing) {
            if (radius > minRadius) {
                radius -= 2;
            } else {
                isCompressing = false;
            }
        } else if (radius < maxRadius) {
            radius += 1;
        }
        repaint(); // Перерисовываем панель
    }
    private void triggerCompression() {
        isCompressing = true;
    }
}
