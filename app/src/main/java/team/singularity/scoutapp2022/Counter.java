/*
 * EZPZ way 2 mk a ANDROID counter (0f numb3rs) + control it w/o having 2 use lots of boilerplate c0de :)
 * Copyright (c) SKYLAR BLEED, 2020
 *
 * THIS SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, NOT BREAKING YOUR STUFF WITH THIS
 * AND BREAKING COMPUTERS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 * REGENTS OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * BREAKING YOUR SHIT; LOSS OF BRAIN CELLS WHILE CONTRIBUTING;
 * OR ANNOYING, UNFIXABLE BUGS) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS AWFUL CODE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */
package team.singularity.scoutapp2022;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

/// A class that encapsulates two buttons and a text box
public class Counter {
    private int count = 0;
    public final ImageButton addBtn, subBtn;
    public final TextView counterTv;

    /// Get count
    public int getCount() {
        return count;
    }

    /// Increment count
    public void inc(int a) {
        count += a;
        display();
    }

    /// Decrement count
    public void dec(int a) {
        if (count < 1) return;
        count -= a;
        display();
    }

    public void setCount(int i) {
        count = i;
        display();
    }

    /// Display count in counter
    public void display() {
        counterTv.setText(String.valueOf(count));
    }

    Counter(ImageButton add, ImageButton sub, TextView counter) {
        // Initialize buttons and textView
        this.addBtn = add;
        this.subBtn = sub;
        counterTv = counter;

        // Add listeners to buttons
        // TODO remove boilerplate
        this.addBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                inc(1);
            }
        });

        this.subBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dec(1);
            }
        });
    }

    Counter(View counter) {
        //TODO: This looks ugly reusing "view.findViewById(counterId)" should fix that
        this(counter.findViewById(R.id.addBtn),
                counter.findViewById(R.id.subBtn),
                counter.findViewById(R.id.counterTv));
    }
}
